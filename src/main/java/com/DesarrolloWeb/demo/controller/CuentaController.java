package com.DesarrolloWeb.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class CuentaController {
  
    @GetMapping("/cuenta")
    public String mostrarLogin(Model model) {
       
        return "cuenta/listado"; 
    }

 
    @PostMapping("/login")
    public String procesarLogin(@RequestParam String usuario,
                                @RequestParam String password,
                                Model model) {

       
            return "cuenta/listado"; 
        }
    

   
    @GetMapping("/registro")
    public String mostrarRegistro(Model model) {
        return "cuenta/detalle"; 
    }

    
    @PostMapping("/registro")
    public String registrarUsuario(@RequestParam String nombreCompleto,
                                   @RequestParam String telefono,
                                   @RequestParam String correo,
                                   @RequestParam String usuario,
                                   @RequestParam String password,
                                   Model model) {

        

        return "redirect:/cuenta"; 
    }
}



