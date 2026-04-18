package com.DesarrolloWeb.demo.controller;

import com.DesarrolloWeb.demo.service.CategoriaService;
import com.DesarrolloWeb.demo.service.PlacaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class IndexController {
    
    private final PlacaService placaService;
    private final CategoriaService categoriaService;

    public IndexController(PlacaService placaService, CategoriaService categoriaService) {
        this.placaService = placaService;
        this.categoriaService = categoriaService;
    }
    
    @GetMapping("/")
    public String inicio(Model model) {
        return "index"; 
    }
}

