package willyworking.com.controllers;

import willyworking.com.models.Cuenta;
import willyworking.com.models.Cliente;
import willyworking.com.services.CuentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import java.util.List;
import java.time.LocalDateTime;

// @PreAuthorize("isAuthenticated()") 
@Controller
@RequestMapping("/cuentasRuta")
@CrossOrigin("*")
public class CuentaController {
    @Autowired
    private CuentaService cuentaService;
    
    @GetMapping(value = {"/", ""})
    public String index(Model model) {
        model.addAttribute("clientes", cuentaService.findAllClientes());
        return "cuentasTemplate";
    }

    @GetMapping("/buscar")
    public String buscar( 
        @RequestParam(required = false) String nombre, Model model) {
        if (nombre == null) {
            nombre = "";
        }
        model.addAttribute("cuentas", cuentaService.findAllWithCliente(nombre));
        return "cuentasTbodyTemplate";
    }

    @PostMapping("/guardar")
    @ResponseBody
    public String guardar(@RequestParam(required = false) Long id,
                         @RequestParam Long idCliente,
                         @RequestParam double monto) {
        try {
            Cuenta cuenta = new Cuenta();
            if (id != null) {
                cuenta.setId(id);
            }
            
            Cliente cliente = new Cliente();
            cliente.setId(idCliente);
            cuenta.setCliente(cliente);
            
            cuenta.setMonto(monto);
            cuenta.setFechaHora(LocalDateTime.now());
            
            cuentaService.save(cuenta);
            return "correcto";
        } catch (Exception e) {
            return "error al guardar la cuenta: " + e.getMessage();
        }
    }

    @GetMapping("/editar/{id}")
    @ResponseBody
    public Cuenta editar(@PathVariable Long id) {
        try {   
            return cuentaService.findById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error al editar la cuenta: " + e.getMessage());
        }
    }

    @GetMapping("/eliminar/{id}")
    @ResponseBody
    public String eliminar(@PathVariable Long id) {
        try {
            cuentaService.delete(id);
            return "correcto";
        } catch (Exception e) {
            return "error al eliminar la cuenta: " + e.getMessage();
        }
    }
    
}