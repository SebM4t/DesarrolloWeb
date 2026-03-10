/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.DesarrolloWeb.demo.controller;

import com.DesarrolloWeb.demo.domain.Categoria;
import com.DesarrolloWeb.demo.domain.Placa;
import com.DesarrolloWeb.demo.service.CategoriaService;
import com.DesarrolloWeb.demo.service.PlacaService;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/mascotas")
public class MascotaController {

    private final PlacaService placaService;
    private final CategoriaService categoriaService;

    public MascotaController(PlacaService placaService, CategoriaService categoriaService) {
        this.placaService = placaService;
        this.categoriaService = categoriaService;
    }

// Lista completa del catálogo
    @GetMapping
    public String catalogo(
            @RequestParam(required = false) Integer idCategoria,
            @RequestParam(required = false) String material,
            Model model) {

        List<Placa> listaCompleta;
        List<Categoria> categorias = categoriaService.listarTodas();

        if (idCategoria != null) {
            listaCompleta = placaService.listarPorCategoria(idCategoria);
        } else if (material != null && !material.isEmpty()) {
            listaCompleta = placaService.listarPorMaterial(material);
        } else {
            listaCompleta = placaService.listarDisponibles();
        }

        List<Placa> placas = listaCompleta.stream()
                .filter(p -> p.getCategoria() != null && "Placas Conmemorativas".equalsIgnoreCase(p.getCategoria().getNombre()))
                .toList();
        
        List<Placa> mascotas = listaCompleta.stream()
                .filter(p -> p.getCategoria() != null && "Placas para Mascotas".equalsIgnoreCase(p.getCategoria().getNombre()))
                .toList();

        model.addAttribute("placas", placas);
        model.addAttribute("mascotas", mascotas);
        model.addAttribute("categorias", categorias);
        model.addAttribute("categoriaSeleccionada", idCategoria);
        model.addAttribute("materialSeleccionado", material);

        return "mascotas/listado";
    }

    @GetMapping("/{idPlaca}")
    public String detalle(@PathVariable Integer idPlaca, Model model) {
        Placa placa = placaService.buscarPorId(idPlaca);
        if (placa == null) {
            return "redirect:/mascotas";
        }
        model.addAttribute("placa", placa);
        return "mascotas/detalle";
    }

}
