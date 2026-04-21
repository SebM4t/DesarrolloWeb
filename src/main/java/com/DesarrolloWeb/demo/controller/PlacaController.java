package com.DesarrolloWeb.demo.controller;

import com.DesarrolloWeb.demo.domain.Placa;
import com.DesarrolloWeb.demo.repository.PlacaRepository;
import com.DesarrolloWeb.demo.service.CategoriaService;
import com.DesarrolloWeb.demo.service.PlacaService;
import java.util.Locale;
import jakarta.validation.Valid;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/placa")
public class PlacaController {

    private final PlacaService placaService;
    private final CategoriaService categoriaService;
    private final MessageSource messageSource;

    public PlacaController(PlacaService placaService, CategoriaService categoriaService, MessageSource messageSource) {
        this.placaService = placaService;
        this.categoriaService = categoriaService;
        this.messageSource = messageSource;
    }

    @GetMapping("/listado")
    public String listado(Model model) {
        var placas = placaService.listarTodas();
        model.addAttribute("placas", placas);
        model.addAttribute("totalPlacas", placas.size());
        model.addAttribute("categorias", categoriaService.listarTodas());
        model.addAttribute("placa", new Placa());

        return "placa/listado";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid Placa placa,
            @RequestParam(value = "imagenFile", required = false) MultipartFile imagenFile,
            RedirectAttributes redirectAttributes) {
        placaService.save(placa, imagenFile);
        redirectAttributes.addFlashAttribute("todoOk",
                messageSource.getMessage("mensaje.actualizado", null, Locale.getDefault()));
        return "redirect:/placa/catalogo";
    }

    @PostMapping("/eliminar")
    public String eliminar(@RequestParam(required = false) Integer idPlaca, RedirectAttributes redirectAttributes) {

        if (idPlaca == null) {
            redirectAttributes.addFlashAttribute("error", "No se especificó la placa a eliminar.");
            return "redirect:/placa/catalogo";
        }

        String titulo = "todoOk";
        String detalle = "mensaje.eliminado";
        try {
            placaService.eliminar(idPlaca);
        } catch (Exception e) {
            titulo = "error";
            detalle = "placa.error";
        }
        redirectAttributes.addFlashAttribute(titulo,
                messageSource.getMessage(detalle, null, Locale.getDefault()));
        return "redirect:/placa/catalogo";
    }

    @GetMapping("/modificar/{idPlaca}")
    public String modificar(@PathVariable("idPlaca") Integer idPlaca, Model model) {
        Placa placa = placaService.buscarPorId(idPlaca);
        model.addAttribute("placa", placa);
        model.addAttribute("categorias", categoriaService.listarTodas());
        return "catalogo/modifica"; 
    }

    @GetMapping("/catalogo")
    public String mostrarCatalogo(Model model) {
        model.addAttribute("placas", placaService.listarPorCategoria(1));
        model.addAttribute("trofeos", placaService.listarPorCategoria(2));
        model.addAttribute("categorias", categoriaService.listarTodas());
        model.addAttribute("placa", new Placa()); 

        return "catalogo/listado";
    }

    @GetMapping("/detalle/{idPlaca}")
    public String detalle(@PathVariable("idPlaca") Integer idPlaca, Model model) {
        Placa placa = placaService.buscarPorId(idPlaca);
        if (placa == null) {
            return "redirect:/placa/catalogo";
        }
        model.addAttribute("placa", placa);
        return "catalogo/detalle";
    }
    

}
