package edu.utn.frro.isi.ds.ventas;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.annotation.SessionScope;


@Scope("session")
@Controller
public class VentaController {

    @Autowired private ClienteRepository clienteRepository;
    @Autowired private ProductoRepository productoRepository;
    Venta venta;
    
    @ModelAttribute("productos")
    public List<Producto> getProductos() {
        return productoRepository.findAll();
    }    
    
	@RequestMapping("/")
    public String iniciarVenta(Model model) {
    	venta = new Venta();
    	//venta.agregarLinea();
        model.addAttribute("venta", venta);
        return "venta";
    }
    
    @RequestMapping(value="/", params={"identificarCliente"})
    public String identificarCliente( @RequestParam(value="cliente.id", required=true) Long clienteId,  Model model) {
    	Cliente cliente = clienteRepository.getOne(clienteId);
    	venta.setCliente(cliente);
        model.addAttribute("venta", venta);
        return "venta";
    }
    
    @RequestMapping(value="/", params={"agregarLinea"})
    public String agregarLinea( Model model) {
    	venta.agregarLinea();
    	model.addAttribute("venta", venta);
        return "venta";
    }
    
    @RequestMapping(value="/", params={"removerLinea"})
    public String removerLinea( Model model, final HttpServletRequest req) {
        final Integer index = Integer.valueOf(req.getParameter("removerLinea"));
    	venta.revoerLinea(index);
    	model.addAttribute("venta", venta);
        return "venta";
    }


}
