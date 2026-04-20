
package com.DesarrolloWeb.demo.controller;

import com.DesarrolloWeb.demo.domain.Usuario;
import com.DesarrolloWeb.demo.service.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class CuentaController {

    private final UsuarioService usuarioService;

    public CuentaController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/cuenta")
    public String mostrarCuenta(Model model) {
        return "cuenta/listado";
    }

    @GetMapping("/registro/nuevo")
    public String registro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "cuenta/detalle";
    }

    @PostMapping("/registro")
    public String registrarUsuario(@ModelAttribute Usuario usuario) {

        // activar usuario nuevo
        usuario.setActivo(true);

        // guardar usuario y cifrar contraseña
        usuarioService.save(usuario, null, true);

        return "redirect:/login";
    }
}

