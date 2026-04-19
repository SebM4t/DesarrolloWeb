package com.DesarrolloWeb.demo.controller;

import com.DesarrolloWeb.demo.domain.Tamanio;
import com.DesarrolloWeb.demo.service.TamanioService;
import jakarta.validation.Valid;
import java.util.Locale;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/tamanio")
public class TamanioController {

    private final TamanioService tamanioService;
    private final MessageSource messageSource;

    public TamanioController(TamanioService tamanioService, MessageSource messageSource) {
        this.tamanioService = tamanioService;
        this.messageSource = messageSource;
    }


    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("tamanio", new Tamanio());
        return "disenar/modifica"; 
    }

    @PostMapping("/guardar")
    public String guardar(@Valid Tamanio tamanio, RedirectAttributes redirectAttributes) {
        tamanioService.guardar(tamanio);
        redirectAttributes.addFlashAttribute("todoOk",
                messageSource.getMessage("mensaje.actualizado", null, Locale.getDefault()));
        return "redirect:/disenar"; 
    }

    @GetMapping("/modificar/{idTamanio}")
    public String modificar(@PathVariable Integer idTamanio, Model model) {
        model.addAttribute("tamanio", tamanioService.buscarPorId(idTamanio));
        return "disenar/modifica"; 
    }

    @GetMapping("/eliminar/{idTamanio}")
    public String eliminar(@PathVariable Integer idTamanio, RedirectAttributes redirectAttributes) {
        String titulo = "todoOk";
        String detalle = "mensaje.eliminado";
        try {
            tamanioService.eliminar(idTamanio);
        } catch (Exception e) {
            titulo = "error";
            detalle = "placa.error";
        }
        redirectAttributes.addFlashAttribute(titulo,
                messageSource.getMessage(detalle, null, Locale.getDefault()));
        return "redirect:/disenar";
    }
}