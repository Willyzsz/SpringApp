package willyworking.com.controllers;

import willyworking.com.models.Producto;
import willyworking.com.models.Cuenta;
import willyworking.com.services.CuentaService;
import willyworking.com.services.ProductoService;
import willyworking.com.services.ClienteService;
import willyworking.com.models.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import java.util.List;
import java.time.LocalDateTime;
import org.springframework.security.access.prepost.PreAuthorize;
import java.util.Map;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;


@PreAuthorize("isAuthenticated()") 
@Controller
@RequestMapping("/comprarRuta")
@CrossOrigin("*")
public class ComprarController {
    @Autowired
    private ProductoService productoService;
    @Autowired
    private CuentaService cuentaService;
    @Autowired
    private ClienteService clienteService;


    @GetMapping(value = {"/", ""})
    public String index(Model model) {
        model.addAttribute("productos", productoService.findAllWithStock());
        return "comprarTemplate";
    }

    @GetMapping("/buscar")
    public String buscar(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String nombre = auth.getName();
        model.addAttribute("cuentas", cuentaService.findAllWithCliente(nombre));
        return "comprarTbodyTemplate";
    }

    @GetMapping("/obtenerProducto")
    @ResponseBody
    public Producto obtenerProducto(@RequestParam Long idProducto) {
        try {
            return productoService.findById(idProducto);
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener el producto: " + e.getMessage());
        }
    }

    @PostMapping("/guardar")
    @ResponseBody
    public String guardar(@RequestBody Map<String, Object> data) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String nombre = auth.getName();

            Cliente cliente = clienteService.findByNombre(nombre);

            Map<String, Object> productoData = (Map<String, Object>) data.get("producto");
            Producto producto = new ObjectMapper().convertValue(productoData, Producto.class);

            int cantidad = (int) data.get("cantidad");
            producto.setStock(producto.getStock() - cantidad);
            productoService.save(producto);

            Cuenta cuenta = cuentaService.findByIdWithCliente(cliente.getId());
            cuenta.setMonto(cuenta.getMonto() + producto.getPrecio() * cantidad);
            cuentaService.save(cuenta);
            
            return "correcto";
        } catch (Exception e) {
            return "Error al guardar el producto: " + e.getMessage();
        }
    }


    
}