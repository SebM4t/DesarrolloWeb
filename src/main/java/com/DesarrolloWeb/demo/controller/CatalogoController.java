package com.DesarrolloWeb.demo.controller;
import com.DesarrolloWeb.demo.model.Placa;
import com.DesarrolloWeb.demo.model.Categoria;
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
public class CatalogoController {
    
private final PlacaService placaService;
private final CategoriaService categoriaService;

public CatalogoController(PlacaService placaService, CategoriaService categoriaService) {
this.placaService = placaService;
this.categoriaService = categoriaService;
}

// Lista completa del catálogo
@GetMapping
public String catalogo(
@RequestParam(required = false) Long categoriaId,
@RequestParam(required = false) String material,
Model model) {
List<Placa> placas;
List<Categoria> categorias = categoriaService.listarTodas();
if (categoriaId != null) {
placas = placaService.listarPorCategoria(categoriaId);
} else if (material != null && !material.isEmpty()) {
placas = placaService.listarPorMaterial(material);
} else {
placas = placaService.listarDisponibles();
}
model.addAttribute("placas", placas);
model.addAttribute("categorias", categorias);
model.addAttribute("categoriaSeleccionada", categoriaId);
model.addAttribute("materialSeleccionado", material);
return "catalogo";
}

// Detalle de una placa
@GetMapping("/{id}")
public String detalle(@PathVariable Long id, Model model) {
Placa placa = placaService.buscarPorId(id);
if (placa == null) {
return "redirect:/catalogo";
}
model.addAttribute("placa", placa);
return "detalle";
}

}
