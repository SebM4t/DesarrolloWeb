package com.DesarrolloWeb.demo.controller;

import com.DesarrolloWeb.demo.domain.Categoria;
import com.DesarrolloWeb.demo.service.CategoriaService;
import jakarta.validation.Valid;
import java.util.Locale;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/categoria")
public class CategoriaController {

    private final CategoriaService categoriaService;
    private final MessageSource messageSource;

    public CategoriaController(CategoriaService categoriaService, MessageSource messageSource) {
        this.categoriaService = categoriaService;
        this.messageSource = messageSource;
    }

    @GetMapping("/listado")
    public String listado(Model model) {
        var categorias = categoriaService.listarTodas();
        model.addAttribute("categorias", categorias);
        model.addAttribute("totalCategorias", categorias.size());
        return "/categoria/listado";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid Categoria categoria, RedirectAttributes redirectAttributes) {
        // Nota: Si no usas imágenes aún, eliminamos el MultipartFile para que no falle
        categoriaService.guardar(categoria);
        
        // Mensaje de éxito usando tu MessageSource
        redirectAttributes.addFlashAttribute("todoOk", 
            messageSource.getMessage("mensaje.actualizado", null, Locale.getDefault()));

        return "redirect:/categoria/listado";
    }

    // Eliminar con manejo de excepciones (muy robusto)
    @PostMapping("/eliminar")
    public String eliminar(@RequestParam Integer idCategoria, RedirectAttributes redirectAttributes) {
        String titulo = "todoOk";
        String detalle = "mensaje.eliminado";
        
        try {
            categoriaService.eliminar(idCategoria);
        } catch (Exception e) {
            // Si tiene placas asociadas o no existe, lanzará error
            titulo = "error";
            detalle = "categoria.error03"; // Ajusta según tus properties
        }
        
        redirectAttributes.addFlashAttribute(titulo, 
            messageSource.getMessage(detalle, null, Locale.getDefault()));
        
        return "redirect:/categoria/listado";
    }

    // Modificar: Carga los datos en el formulario
    @GetMapping("/modificar/{idCategoria}")
    public String modificar(@PathVariable("idCategoria") Integer idCategoria, Model model, RedirectAttributes redirectAttributes) {
        var categoria = categoriaService.buscarPorId(idCategoria);
        
        if (categoria == null) {
            redirectAttributes.addFlashAttribute("error", 
                messageSource.getMessage("categoria.error01", null, Locale.getDefault()));
            return "redirect:/categoria/listado";
        }
        
        model.addAttribute("categoria", categoria);
        return "/categoria/modifica"; // Esta es la página con el formulario
    }
    
    
}