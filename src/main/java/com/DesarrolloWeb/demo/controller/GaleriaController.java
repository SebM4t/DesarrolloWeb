/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.DesarrolloWeb.demo.controller;

import com.DesarrolloWeb.demo.domain.Placa;
import com.DesarrolloWeb.demo.service.CategoriaService;
import com.DesarrolloWeb.demo.service.PlacaService;
import jakarta.validation.Valid;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.context.MessageSource;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author andre
 */
@Controller
@RequestMapping("/galeria")
public class GaleriaController {
    
    @Autowired
    private PlacaService placaService;
    
    @Autowired
    private CategoriaService categoriaService;
    
    @Autowired
    private MessageSource messageSource;

    @GetMapping("/listado")
    public String listado(Model model) {
        var placas = placaService.listarTodas();
        var categorias = categoriaService.listarTodas(); 

        model.addAttribute("placas", placas);
        model.addAttribute("categorias", categorias);

        model.addAttribute("precioInf", 0);
        model.addAttribute("precioSup", 0);
        return "galeria/listado";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid Placa placa,
            @RequestParam(value = "imagenFile", required = false) MultipartFile imagenFile,
            RedirectAttributes redirectAttributes) {
        placaService.save(placa, imagenFile);
        redirectAttributes.addFlashAttribute("todoOk",
                messageSource.getMessage("mensaje.actualizado", null, Locale.getDefault()));
        return "redirect:/galeria/listado";
    }

    @PostMapping("/eliminar")
    public String eliminar(@RequestParam(required = false) Integer idPlaca, RedirectAttributes redirectAttributes) {

        if (idPlaca == null) {
            redirectAttributes.addFlashAttribute("error", "No se especificó la placa a eliminar.");
            return "redirect:/galeria/listado";
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
        return "redirect:/galeria/listado";
    }

    @GetMapping("/modificar/{idPlaca}")
    public String modificar(@PathVariable("idPlaca") Integer idPlaca, Model model) {
        Placa placa = placaService.buscarPorId(idPlaca);
        model.addAttribute("placa", placa);
        model.addAttribute("categorias", categoriaService.listarTodas());
        return "galeria/modifica"; 
    }
    
    @PostMapping("/query1")
    public String consultaRangoPrecios(@RequestParam(value = "precioInf") double precioInf,
                                       @RequestParam(value = "precioSup") double precioSup, 
                                       Model model) {
        var placas = placaService.consultaSQL(precioInf, precioSup);
        model.addAttribute("placas", placas);
        model.addAttribute("precioInf", precioInf);
        model.addAttribute("precioSup", precioSup);
        return "galeria/listado";
    }
    @PostMapping("/query2")
    public String consultaPorNombre(@RequestParam(value = "nombre") String nombre, Model model) {
        var placas = placaService.consultaNombreSQL(nombre);
        model.addAttribute("placas", placas);
        return "galeria/listado";
    }
}