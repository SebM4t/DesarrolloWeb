package com.DesarrolloWeb.demo.controller;

import com.DesarrolloWeb.demo.service.MaterialService;
import com.DesarrolloWeb.demo.service.TamanioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/disenar")
public class DisenarController {

    private final MaterialService materialService;
    private final TamanioService tamanioService;

    public DisenarController(MaterialService materialService, TamanioService tamanioService) {
        this.materialService = materialService;
        this.tamanioService = tamanioService;
    }

    @GetMapping
    public String disenar(Model model) {
        model.addAttribute("materiales", materialService.listarTodos());
        model.addAttribute("tamanios",   tamanioService.listarTodos());
        return "disenar/listado";
    }
}