package edu.utn.frro.isi.ds.ventas;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Scope("session")
/* Rest should be stateless but we use a session as this is just an educational example for
 * university students to be aligned with the session controller approach of book: Larman - Applying UML and Patterns.
 * We use a rest controller to consume json easier from Angular.
 */

public class VentaRestController {

    private static final String template = "Hello, %s!";
   
    private final ClienteRepository clienteRepository;
    private final ProductoRepository productoRepository;
    private final VentaRepository ventaRepository;
    private Venta venta;

	@Autowired
	public VentaRestController(ClienteRepository customerRepository, ProductoRepository productRepository, VentaRepository ventaRepository) {
		this.clienteRepository = customerRepository;
		this.productoRepository = productRepository;
		this.ventaRepository = ventaRepository;
	}

	 @RequestMapping("/venta/recargarVenta")
	    public Venta recargarVenta() {
	    	return venta;
	 }
	 
	 @RequestMapping("/venta/reiniciarVenta")
	    public Venta reiniciarVenta() {
		 	venta = new Venta();
	    	return venta;
	 }
	
    @RequestMapping("/venta/iniciarVenta")
    public Venta iniciarVenta(@RequestParam(value="clienteId") Long id) {
    	Cliente cliente = clienteRepository.findOne(id);
    	
    	venta = new Venta(cliente);
    	
    	return venta;
    }
    
    @RequestMapping("/venta/actualizarProducto")
    public Venta actualizarLinea(@RequestParam(value="index") Integer index, @RequestParam(value="id") Long id, @RequestParam(value="cantidad", defaultValue="1") Integer cantidad) {
    	Producto producto = productoRepository.findOne(id);
    	if (producto == null)
    		return venta;
    	LineaVenta linea = venta.getLineaAt(index);
    	if (linea == null) {
    		agregarProducto(producto, cantidad);
    	} else {
	    	linea.setProducto(producto);
	    	linea.setCantidad(cantidad);
    	}
		return venta;
    }
    
    public Venta agregarProducto(Producto producto , Integer cantidad) {
    	if (producto!=null) {
	    	venta.agregarProducto(producto, cantidad);
    	}
    	return venta;
    }
    
    @RequestMapping("/venta/actualizarCantidad")
    public Venta actualizarCantidad(@RequestParam(value="index") Integer index, @RequestParam(value="cantidad", defaultValue="1") Integer cantidad) {
    	if (cantidad<1) {
    		return venta;
    	}
    	LineaVenta linea = venta.getLineaAt(index);
    	if (linea != null) {
	    	linea.setCantidad(cantidad);
    	}
		return venta;
    }
    
    @RequestMapping("/venta/agregarLinea")
    public Venta agregarLinea() {
    	venta.agregarLinea();
		return venta;
    }
    
    @RequestMapping("/venta/confirmarCompra")
    public Venta confirmarCompra() {
    	ventaRepository.save(venta);
		return venta;
    }
    
    
    @RequestMapping(value="/ventas", method=RequestMethod.POST)
    public Venta confirmarCompraPost(@RequestBody Venta ventaDTO) {
    	venta=ventaDTO;
    	
    	venta = new Venta(clienteRepository.findOne(ventaDTO.getCliente().getId()));
    	for (LineaVenta lineaVenta : ventaDTO.getLineas()) {
    		venta.agregarProducto(productoRepository.findOne(lineaVenta.getProductoId()), lineaVenta.getCantidad());
    	}
    	
    	ventaRepository.save(venta);
    	
		return venta;
    }
    
   
}
