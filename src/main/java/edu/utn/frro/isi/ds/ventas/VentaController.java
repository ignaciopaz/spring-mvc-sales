package edu.utn.frro.isi.ds.ventas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class VentaController {

    @Autowired private ClienteRepository clienteRepository;

	@RequestMapping("/")
    public String iniciarVenta(Model model) {
    	Venta venta = new Venta(new Cliente());
        model.addAttribute("venta", venta);
        return "venta";
    }
    
    @RequestMapping(value="/", params={"identificarCliente"})
    public String identificarCliente(@ModelAttribute Venta venta, @RequestParam(value="cliente.id", required=false) Long clienteId,  Model model) {
    	Cliente cliente = clienteRepository.getOne(clienteId);
    	venta.setCliente(cliente);
        //model.addAttribute("name", name);
        return "venta";
    }


}
