package com.DesarrolloWeb.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CuentaController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/cuenta")
    public String mostrarCuenta(Model model) {
        return "cuenta/listado";
    }

    @PostMapping("/registro")
    public String registrarUsuario(@RequestParam String nombreCompleto,
            @RequestParam String telefono,
            @RequestParam String correo,
            @RequestParam String usuario,
            @RequestParam String password,
            Model model) {

        return "redirect:/login";
    }

    @GetMapping("/registro/nuevo")
    public String registro() {
        return "cuenta/detalle";
    }
}
