package com.DesarrolloWeb.demo.controller;

import com.DesarrolloWeb.demo.service.CategoriaService;
import com.DesarrolloWeb.demo.service.PlacaService;
import org.springframework.stereotype.Controller;


@Controller
public class IndexController {
    
    private final PlacaService placaService;
    private final CategoriaService categoriaService;

    public IndexController(PlacaService placaService, CategoriaService categoriaService) {
        this.placaService = placaService;
        this.categoriaService = categoriaService;
    }

    

}
