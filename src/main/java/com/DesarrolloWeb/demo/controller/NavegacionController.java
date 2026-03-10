package com.DesarrolloWeb.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NavegacionController {

    @GetMapping("/disenar")
    public String disenar() { return "disenar/disenar"; }

    @GetMapping("/galeria")
    public String galeria() { return "galeria/galeria"; }

    @GetMapping("/funciones")
    public String funciones() { return "funciones/funciones"; }

    @GetMapping("/faq")
    public String faq() { return "faq/faq"; }

    @GetMapping("/soporte")
    public String soporte() { return "soporte/soporte"; }
    
    @GetMapping("/carrito")
    public String carrito() { return "carrito/carrito"; }
    
    @GetMapping("/cuenta")
    public String cuenta() { return "carrito/carrito"; }
}