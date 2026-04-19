package com.DesarrolloWeb.demo.controller;

import com.DesarrolloWeb.demo.domain.Material;
import com.DesarrolloWeb.demo.service.MaterialService;
import jakarta.validation.Valid;
import java.util.Locale;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/material")
public class MaterialController {

    private final MaterialService materialService;
    private final MessageSource messageSource;


    public MaterialController(MaterialService materialService, MessageSource messageSource) {
        this.materialService = materialService;
        this.messageSource = messageSource;
    }


    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("material", new Material());
        return "disenar/modifica"; 
    }

    @PostMapping("/guardar")
    public String guardar(@Valid Material material, RedirectAttributes redirectAttributes) {
        materialService.guardar(material);
        redirectAttributes.addFlashAttribute("todoOk",
                messageSource.getMessage("mensaje.actualizado", null, Locale.getDefault()));
        return "redirect:/disenar"; 
    }

    @GetMapping("/modificar/{idMaterial}")
    public String modificar(@PathVariable Integer idMaterial, Model model) {
        model.addAttribute("material", materialService.buscarPorId(idMaterial));
        return "disenar/modifica"; 
    }

    @GetMapping("/eliminar/{idMaterial}")
    public String eliminar(@PathVariable Integer idMaterial, RedirectAttributes redirectAttributes) {
        String titulo = "todoOk";
        String detalle = "mensaje.eliminado";
        try {
            materialService.eliminar(idMaterial);
        } catch (Exception e) {
            titulo = "error";
            detalle = "placa.error";
        }
        redirectAttributes.addFlashAttribute(titulo,
                messageSource.getMessage(detalle, null, Locale.getDefault()));
        return "redirect:/disenar";
    }
}