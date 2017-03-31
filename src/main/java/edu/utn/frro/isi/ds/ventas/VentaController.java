package edu.utn.frro.isi.ds.ventas;

import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.annotation.SessionScope;

@Controller
public class VentaController {

    @Autowired private ClienteRepository clienteRepository;
    @Autowired private ProductoRepository productoRepository;
    @Autowired private VentaRepository ventaRepository;
    Venta venta;
    
    @ModelAttribute("productos")
    public List<Producto> getProductos() {
        return productoRepository.buscarProductosEnStock();
    }    
    
	@RequestMapping("/")
    public String iniciarVenta(Model model) {
    	venta = new Venta();
        model.addAttribute("venta", venta);
        return "venta";
    }
	@RequestMapping("/venta")
	public String verVenta(Model model) {
		model.addAttribute("venta", venta);
		return "venta";
	}
    
    @RequestMapping(value="/", params={"identificarCliente"})
    public String identificarCliente( @RequestParam(value="cliente.id", required=true) Long clienteId,  Model model) {
    	try {
    		Cliente cliente = clienteRepository.getOne(clienteId);
    		if (cliente !=null && cliente.getId() != null) {
        		venta.setCliente(cliente);   	
        	}
    	} catch (EntityNotFoundException e) {
    	}    	
    	model.addAttribute("venta", venta);
        return "redirect:venta";
    }
    
    @RequestMapping(value="/", params={"agregarLinea"})
    public String agregarLinea(@Valid @RequestParam(value="productoId", required=true) Long productoId, @RequestParam(value="cantidad", required=true) Integer cantidad, Model model) {
    	Producto producto = productoRepository.getOne(productoId);
    	model.addAttribute("error", "Solo quedan "+producto.getCantidadStock()+" unidades del producto "+producto.getDescripcion());
    	if (venta.isPuedeAgregar(producto, cantidad)) {
    		venta.agregarProducto(producto, cantidad);
    		model.addAttribute("venta", venta);
            return "redirect:venta";
    	} else {
    		model.addAttribute("venta", venta);
    		model.addAttribute("error", "Solo quedan " +producto.getCantidadStock() +" unidades del producto "+producto.getDescripcion());
    		return "venta";
    	}    	
    }
    
    @RequestMapping(value="/", params={"removerLinea"})
    public String removerLinea(@RequestParam(value="removerLinea", required=true) Integer index, Model model) {
    	venta.removerLinea(index);
    	model.addAttribute("venta", venta);
        return "redirect:venta";
    }
    
    @RequestMapping(value="/", params={"Comprar"})
    public String comprar( Model model) {
    	venta.confirmar();
    	ventaRepository.save(venta);
    	productoRepository.save(venta.getProductos());
    	
    	model.addAttribute("venta", venta);
        return "redirect:venta";
    }

    
}
