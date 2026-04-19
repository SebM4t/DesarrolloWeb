/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.DesarrolloWeb.demo.controller;

import com.DesarrolloWeb.demo.service.CategoriaService;
import com.DesarrolloWeb.demo.service.PlacaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author andre
 */
@Controller
@RequestMapping("/galeria")
public class GaleriaController {
    
    @Autowired
    private PlacaService placaService;

    // 1. Carga inicial de la Galería
    @GetMapping("/listado")
    public String listado(Model model) {
        // Usamos tu método para traer todas las placas
        var placas = placaService.listarTodas();
        model.addAttribute("placas", placas);
        
        // Enviamos valores vacíos iniciales para los filtros
        model.addAttribute("precioInf", 0);
        model.addAttribute("precioSup", 0);
        return "/galeria/listado";
    }

    // 2. Filtro por Rango de Precios (Copiado de la lógica de Tienda)
    @PostMapping("/query1")
    public String consultaRangoPrecios(@RequestParam(value = "precioInf") double precioInf,
                                       @RequestParam(value = "precioSup") double precioSup, 
                                       Model model) {
        // Llamamos al método SQL que adaptamos en el Service
        var placas = placaService.consultaSQL(precioInf, precioSup);
        model.addAttribute("placas", placas);
        
        // Devolvemos los precios para que el usuario vea qué filtró
        model.addAttribute("precioInf", precioInf);
        model.addAttribute("precioSup", precioSup);
        return "galeria/listado";
    }

    // 3. Filtro por Nombre o Descripción
    @PostMapping("/query2")
    public String consultaPorNombre(@RequestParam(value = "nombre") String nombre, Model model) {
        var placas = placaService.consultaNombreSQL(nombre);
        model.addAttribute("placas", placas);
        return "galeria/listado";
    }
}