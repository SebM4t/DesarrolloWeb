package com.DesarrolloWeb.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NavegacionController {

    @GetMapping("/disenar")
    public String disenar() { return "disenar/listado"; }

    @GetMapping("/galeria")
    public String galeria() { return "galeria/listado"; }

    @GetMapping("/funciones")
    public String funciones() { return "funciones/listado"; }

    @GetMapping("/faq")
    public String faq() { return "faq/listado"; }

    @GetMapping("/soporte")
    public String soporte() { return "soporte/listado"; }
    
    @GetMapping("/carrito")
    public String carrito() { return "carrito/listado"; }
    
//     @GetMapping("/mascotas")
//    public String mascotas() { return "mascotas/listado"; }
    
//    @GetMapping("/cuenta")
//
//    public String cuenta() { return "cuenta/cuenta"; }
//
//    public String cuentaListado() { return "cuenta/listado"; }

}