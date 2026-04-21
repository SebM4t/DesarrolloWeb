package com.DesarrolloWeb.demo.controller;

import com.DesarrolloWeb.demo.domain.Soporte;
import com.DesarrolloWeb.demo.service.SoporteService;
import java.util.Locale;
import jakarta.validation.Valid;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/soporte")
public class SoporteController {

    private final SoporteService soporteService;
    private final MessageSource messageSource;

    public SoporteController(SoporteService soporteService, MessageSource messageSource) {
        this.soporteService = soporteService;
        this.messageSource = messageSource;
    }


    @GetMapping
    public String listado(Model model) {
        var soportes = soporteService.listarTodas();
        model.addAttribute("soportes", soportes);
        model.addAttribute("totalSoporte", soportes.size());
        model.addAttribute("soporte", new Soporte());

        return "soporte/listado";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid Soporte soporte,
            RedirectAttributes redirectAttributes) {
        soporteService.guardar(soporte);
        redirectAttributes.addFlashAttribute("todoOk",
                messageSource.getMessage("mensaje.actualizado", null, Locale.getDefault()));
        return "redirect:/soporte";
    }

    @PostMapping("/eliminar")
    public String eliminar(@RequestParam Integer idSoporte, RedirectAttributes redirectAttributes) {
        String titulo = "todoOk";
        String detalle = "mensaje.eliminado";
        try {
            soporteService.eliminar(idSoporte);
        } catch (Exception e) {
            titulo = "error";
            detalle = "soporte.error";
        }
        redirectAttributes.addFlashAttribute(titulo,
                messageSource.getMessage(detalle, null, Locale.getDefault()));
        return "redirect:/soporte";
    }

    

}
