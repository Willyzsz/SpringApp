package willyworking.com.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import jakarta.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import org.springframework.security.access.prepost.PreAuthorize;

@Controller
@RequestMapping("/usuariosRuta")
@CrossOrigin("*")
public class UsuarioController {
    
    @GetMapping(value = {"/", ""})
    public String index() {
        return "loginTemplate";
    }

    @GetMapping("/checkSession")
    @ResponseBody
    public Map<String, Object> checkSession() {
        Map<String, Object> response = new HashMap<>();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getPrincipal())) {
            response.put("login", true);
            response.put("username", auth.getName());
            response.put("role", auth.getAuthorities().iterator().next().getAuthority());
        } else {
            response.put("login", false);
            response.put("username", null);
            response.put("role", null);
        }
        return response;
    }

    @PreAuthorize("isAuthenticated()") 
    @GetMapping("/cerrarSesion")
    @ResponseBody
    public String cerrarSesion(HttpSession session) {
        session.invalidate();
        SecurityContextHolder.clearContext();
        return "correcto";
    }
}