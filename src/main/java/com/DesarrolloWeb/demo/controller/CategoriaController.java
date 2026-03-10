package com.DesarrolloWeb.demo.controller;

import com.DesarrolloWeb.demo.domain.Placa;
import com.DesarrolloWeb.demo.domain.Categoria;
import com.DesarrolloWeb.demo.service.PlacaService;
import com.DesarrolloWeb.demo.service.CategoriaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@Controller
@RequestMapping("/catalogo")
public class CategoriaController {

    private final PlacaService placaService;
    private final CategoriaService categoriaService;

    public CategoriaController(PlacaService placaService, CategoriaService categoriaService) {
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

        List<Placa> trofeos = listaCompleta.stream()
                .filter(p -> p.getCategoria() != null && "Trofeos y Reconocimientos".equalsIgnoreCase(p.getCategoria().getNombre()))
                .toList();

        model.addAttribute("placas", placas);
        model.addAttribute("trofeos", trofeos);
        model.addAttribute("categorias", categorias);
        model.addAttribute("categoriaSeleccionada", idCategoria);
        model.addAttribute("materialSeleccionado", material);

        return "catalogo/listado";
    }

    @GetMapping("/{idPlaca}")
    public String detalle(@PathVariable Integer idPlaca, Model model) {
        Placa placa = placaService.buscarPorId(idPlaca);
        if (placa == null) {
            return "redirect:/catalogo";
        }
        model.addAttribute("placa", placa);
        return "catalogo/detalle";
    }

}
