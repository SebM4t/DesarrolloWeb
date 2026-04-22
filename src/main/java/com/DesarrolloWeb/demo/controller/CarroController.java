package com.DesarrolloWeb.demo.controller;

import com.DesarrolloWeb.demo.domain.Factura;
import com.DesarrolloWeb.demo.domain.Item;
import com.DesarrolloWeb.demo.domain.Usuario;
import com.DesarrolloWeb.demo.service.CarroService;
import com.DesarrolloWeb.demo.service.FacturaService;
import com.DesarrolloWeb.demo.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/carro")
public class CarroController {

    private final CarroService carroService;
    private final UsuarioService usuarioService;
    private final FacturaService facturaService;

    public CarroController(CarroService carroService, UsuarioService usuarioService, FacturaService facturaService) {
        this.carroService = carroService;
        this.usuarioService = usuarioService;
        this.facturaService = facturaService;
    }

    @GetMapping
    public String redirectToListado() {
        return "redirect:/carro/listado";
    }

    @GetMapping("/listado")
    public String listado(HttpSession session, Model model) {
        List<Item> carro = carroService.obtenerCarro(session);

        model.addAttribute("carroItems", carro);
        model.addAttribute("totalCarro", carroService.calcularTotal(carro));

        return "carro/listado";
    }

    @PostMapping("/agregarPlaca")
    public String agregarPlaca(
            @RequestParam("idPlaca") Integer idPlaca,
            HttpSession session,
            Model model) {
        try {
            List<Item> carro = carroService.obtenerCarro(session);

            carroService.agregarCategoria(carro, idPlaca);

            carroService.guardarCarro(session, carro);

            model.addAttribute("carroItems", carro);
            model.addAttribute("totalCarro", carroService.calcularTotal(carro));

        } catch (RuntimeException e) {
            return "redirect:/carro/listado";
        }
        return "redirect:/carro/listado";
    }

    @PostMapping("/agregarDiseño")
    public String agregarDisenar(
            @RequestParam("idTamanio") Integer idTamanio,
            @RequestParam("idMaterial") Integer idMaterial,
            HttpSession session,
            Model model) {
        try {
            List<Item> carro = carroService.obtenerCarro(session);

            carroService.agregarDisenar(carro, idTamanio, idMaterial);

            carroService.guardarCarro(session, carro);

            model.addAttribute("carroItems", carro);
            model.addAttribute("totalCarro", carroService.calcularTotal(carro));

        } catch (RuntimeException e) {
            return "redirect:/carro/listado";
        }
        return "redirect:/carro/listado";

    }

    @PostMapping("/eliminar/{idPlaca}")
    public String eliminarItemCategoria(
            @PathVariable("idPlaca") Integer idPlaca,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        List<Item> carro = carroService.obtenerCarro(session);
        carroService.eliminarItemCategoria(carro, idPlaca);
        carroService.guardarCarro(session, carro);

        redirectAttributes.addFlashAttribute("mensaje", "Placa eliminada del carro.");
        return "redirect:/carro/listado";
    }

    @PostMapping("/eliminarDiseño")
    public String eliminarItemDisenar(
            @PathVariable("idTamanio") Integer idTamanio,
            @PathVariable("idMaterial") Integer idMaterial,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        List<Item> carro = carroService.obtenerCarro(session);
        carroService.eliminarItemDisenar(carro, idTamanio, idMaterial);
        carroService.guardarCarro(session, carro);

        redirectAttributes.addFlashAttribute("mensaje", "Diseño eliminado del carro.");
        return "redirect:/carro/listado";
    }

    @GetMapping("/modificar/{idPlaca}")
    public String modificarCategoria(
            @PathVariable("idPlaca") Integer idPlaca,
            HttpSession session,
            Model model) {

        List<Item> carro = carroService.obtenerCarro(session);

        Item item = carroService.buscarItemCategoria(carro, idPlaca);

        if (item == null) {
            System.out.println("Hubo problemas");
            return "redirect:/carro/listado";
        }

        model.addAttribute("item", item);

        return "/carro/modifica";
    }

    @GetMapping("/modificarDiseño")
    public String modificarDiseno(
            @PathVariable("idTamanio") Integer idTamanio,
            @PathVariable("idMaterial") Integer idMaterial,
            HttpSession session,
            Model model) {

        List<Item> carro = carroService.obtenerCarro(session);

        Item item = carroService.buscarItemDisenar(carro, idTamanio, idMaterial);

        if (item == null) {
            System.out.println("Hubo problemas");
            return "redirect:/carro/listado";
        }

        model.addAttribute("item", item);

        return "/carro/modifica";
    }

    @GetMapping("/facturar/carro")
    public String facturarCarro(HttpSession session, RedirectAttributes redirectAttributes) {
        System.out.println("Va a facturar");

        List<Item> carro = carroService.obtenerCarro(session);

        if (carro == null || carro.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "El carro está vacío, no se puede facturar.");
            return "redirect:/carro/listado";
        }

        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String username = auth.getName();
            System.out.println("El username es:" + username);
            Usuario usuario = usuarioService.getUsuarioPorUsername(username).get();

            Factura factura = carroService.procesarCompra(carro, usuario);

            carroService.limpiarCarro(session);

            redirectAttributes.addFlashAttribute("idFactura", factura.getIdFactura());
            redirectAttributes.addFlashAttribute("mensaje", "Compra procesada con éxito. Factura Nro: " + factura.getIdFactura());

            System.out.println("Ver la Factura");
            return "redirect:/carro/verFactura?idFactura=" + factura.getIdFactura();

        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", "Error al procesar la compra: " + e.getMessage());
            return "redirect:/carro/listado";
        }
    }

    @GetMapping("/verFactura")
    public String verFactura(@RequestParam("idFactura") Integer idFactura, Model model) {
        if (idFactura == null) {
            return "redirect:/carro/listado";
        }

        Factura factura = facturaService.getFacturaConVentas(idFactura);
        model.addAttribute("factura", factura);
        return "carro/verFactura"; 
    }
}
