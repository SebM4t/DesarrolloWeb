package com.DesarrolloWeb.demo.controller;

import com.DesarrolloWeb.demo.domain.Placa;
import com.DesarrolloWeb.demo.repository.PlacaRepository;
import com.DesarrolloWeb.demo.service.PlacaService;
import java.util.Locale;
import jakarta.validation.Valid;
import java.util.Optional;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/placa") // Cambiamos la ruta base
public class PlacaController {

    private final PlacaService placaService;
    private final MessageSource messageSource;

    public PlacaController(PlacaService placaService, MessageSource messageSource) {
        this.placaService = placaService;
        this.messageSource = messageSource;
    }
    

    @GetMapping("/listado")
    public String listado(Model model) {
        var placas = placaService.listarDisponibles(); // Ajusta según tu método
        model.addAttribute("placas", placas);
        model.addAttribute("totalPlacas", placas.size());
        return "placa/listado"; // Asegúrate de tener este archivo en /templates/placa/listado.html
    }

    @PostMapping("/guardar")
    public String guardar(@Valid Placa placa, @RequestParam MultipartFile imagenFile, RedirectAttributes redirectAttributes) {
        placaService.save(placa, imagenFile); // Asumiendo que tu servicio acepta estos parámetros
        redirectAttributes.addFlashAttribute("todoOk", messageSource.getMessage("mensaje.actualizado", null, Locale.getDefault()));
        return "redirect:/placa/listado";
    }

    @PostMapping("/eliminar")
    public String eliminar(@RequestParam Integer idPlaca, RedirectAttributes redirectAttributes) { 
        String titulo = "todoOk";
        String detalle = "mensaje.eliminado";
        try {
            placaService.eliminar(idPlaca);
        } catch (Exception e) {
            titulo = "error";
            detalle = "placa.error"; // Asegúrate de tener este mensaje en tu archivo messages.properties
        }
        redirectAttributes.addFlashAttribute(titulo, messageSource.getMessage(detalle, null, Locale.getDefault()));
        return "redirect:/placa/listado";
    }

    @GetMapping("/modificar/{idPlaca}")
    public String modificar(@PathVariable("idPlaca") Integer idPlaca, Model model, RedirectAttributes redirectAttributes) {
        Placa placa = placaService.buscarPorId(idPlaca); // Ajusta según tu servicio
        if (placa == null) {
            redirectAttributes.addFlashAttribute("error", messageSource.getMessage("placa.noEncontrada", null, Locale.getDefault()));
            return "redirect:/placa/listado";
        }
        model.addAttribute("placa", placa);
        return "placa/modifica"; // Asegúrate de tener este archivo en /templates/placa/modifica.html
    }

}
