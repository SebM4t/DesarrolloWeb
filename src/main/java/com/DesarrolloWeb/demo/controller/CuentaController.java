package com.DesarrolloWeb.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class CuentaController {
 @GetMapping("/cuenta")
    public String mostrarCuenta() {
        return "cuenta/cuenta";
    }

    @GetMapping("/registro")
    public String mostrarRegistro() {
        return "cuenta/registro";
    }

    @PostMapping("/registro")
    public String registrarUsuario() {
        return "redirect:/cuenta";
    }    
}


