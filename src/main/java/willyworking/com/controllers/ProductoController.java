package willyworking.com.controllers;

import willyworking.com.models.Producto;
import willyworking.com.services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import java.util.List;

// @PreAuthorize("isAuthenticated()") 
// @PreAuthorize("hasRole('ADMIN')") 

@Controller
@RequestMapping("/productosRuta")
@CrossOrigin("*")
public class ProductoController {
    @Autowired
    private ProductoService productoService;
    
    @GetMapping(value = {"/", ""})
    public String index() {
        return "productosTemplate";
    }

//    if (auth.getAuthorities().stream()
//            .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
//     }

    @GetMapping("/buscar")
    public String buscar( 
        @RequestParam(required = false) String nombre, Model model) {
        if (nombre == null) {
            nombre = "";
        }
        model.addAttribute("productos", productoService.findByNombreLike(nombre));
        return "productosTbodyTemplate";
    }

    @PostMapping("/guardar")
    @ResponseBody
    public String guardar(@ModelAttribute Producto producto) {
        try {
            productoService.save(producto);
            return "correcto";
        } catch (Exception e) {
            return "error al guardar el producto: " + e.getMessage();
        }
    }

    @GetMapping("/editar/{id}")
    @ResponseBody
    public Producto editar(@PathVariable Long id) {
        try {   
            return productoService.findById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error al editar el producto: " + e.getMessage());
        }
    }

    @GetMapping("/eliminar/{id}")
    @ResponseBody
    public String eliminar(@PathVariable Long id) {
        try {
            productoService.delete(id);
            return "correcto";
        } catch (Exception e) {
            return "error al eliminar el producto: " + e.getMessage();
        }
    }

    // Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    // String username = auth.getName();
    // Collection<? extends GrantedAuthority> roles = auth.getAuthorities();
    
}