package willyworking.com.controllers;

import willyworking.com.models.Cliente;
import willyworking.com.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;

@PreAuthorize("hasRole('ROLE_admin')") 
@Controller
@RequestMapping("/clientesRuta")
@CrossOrigin("*")
public class ClienteController {
    @Autowired
    private ClienteService clienteService;
    
    @GetMapping(value = {"/", ""})
    public String index() {
        return "clientesTemplate";
    }

    @GetMapping("/buscar")
    public String buscar( 
        @RequestParam(required = false) String nombre, Model model) {
        if (nombre == null) {
            nombre = "";
        }
        model.addAttribute("clientes", clienteService.findByNombreLike(nombre));
        return "clientesTbodyTemplate";
    }

    @PostMapping("/guardar")
    @ResponseBody
    public String guardar(@ModelAttribute Cliente cliente) {
        try {
            clienteService.save(cliente);
            return "correcto";
        } catch (Exception e) {
            return "error al guardar el cliente: " + e.getMessage();
        }
    }

    @GetMapping("/editar/{id}")
    @ResponseBody
    public Cliente editar(@PathVariable Long id) {
        try {   
            return clienteService.findById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error al editar el cliente: " + e.getMessage());
        }
    }

    @GetMapping("/eliminar/{id}")
    @ResponseBody
    public String eliminar(@PathVariable Long id) {
        try {
            clienteService.delete(id);
            return "correcto";
        } catch (Exception e) {
            return "error al eliminar el cliente: " + e.getMessage();
        }
    }
    
}