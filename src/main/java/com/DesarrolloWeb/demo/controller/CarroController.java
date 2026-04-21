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

    // --- 1. MOSTRAR EL CARRO ---
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
            // 1. Obtener el carro de la sesión
            List<Item> carro = carroService.obtenerCarro(session);

            // 2. Ejecutar la lógica de negocio (el Service asume cantidad = 1)
            carroService.agregarDisenar(carro, idTamanio, idMaterial);

            // 3. Guardar el carro actualizado en la sesión
            carroService.guardarCarro(session, carro);

            // 4. Recalcular y actualizar el Model con los datos necesarios
            model.addAttribute("carroItems", carro);
            model.addAttribute("totalCarro", carroService.calcularTotal(carro));

            // 5. Retornar el fragmento HTML
        } catch (RuntimeException e) {
            return "redirect:/carro/listado";
        }
        return "redirect:/carro/listado";

    }

    // --- 3. ELIMINAR ITEM DEL CARRO ---
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

        // 1. Obtener la lista del carro de la sesión
        List<Item> carro = carroService.obtenerCarro(session);

        // 2. Buscar el ítem en la lista del carro
        Item item = carroService.buscarItemCategoria(carro, idPlaca);

        if (item == null) {
            // Manejar el caso de que el ítem no esté en el carro
            System.out.println("Hubo problemas");
            return "redirect:/carro/listado";
        }

        // 3. Pasar el ítem encontrado (con su cantidad actual) al modelo
        model.addAttribute("item", item);

        // 4. Retornar la vista
        return "/carro/modifica";
    }

    @GetMapping("/modificarDiseño")
    public String modificarDiseno(
            @PathVariable("idTamanio") Integer idTamanio,
            @PathVariable("idMaterial") Integer idMaterial,
            HttpSession session,
            Model model) {

        // 1. Obtener la lista del carro de la sesión
        List<Item> carro = carroService.obtenerCarro(session);

        // 2. Buscar el ítem en la lista del carro
        Item item = carroService.buscarItemDisenar(carro, idTamanio, idMaterial);

        if (item == null) {
            // Manejar el caso de que el ítem no esté en el carro
            System.out.println("Hubo problemas");
            return "redirect:/carro/listado";
        }

        // 3. Pasar el ítem encontrado (con su cantidad actual) al modelo
        model.addAttribute("item", item);

        // 4. Retornar la vista
        return "/carro/modifica";
    }

    // --- 4. ACTUALIZAR CANTIDAD DESDE LA VISTA ---
    // --- 5. PROCESAR COMPRA (CHECKOUT) ---
    @GetMapping("/facturar/carro")
    public String facturarCarro(HttpSession session, RedirectAttributes redirectAttributes) {
        System.out.println("Va a facturar");

        List<Item> carro = carroService.obtenerCarro(session);

        if (carro == null || carro.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "El carro está vacío, no se puede facturar.");
            return "redirect:/carro/listado";
        }

        try {
            // Obtención del usuario autenticado*
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String username = auth.getName();
            System.out.println("El username es:" + username);
            Usuario usuario = usuarioService.getUsuarioPorUsername(username).get();

            // 1. La lógica transaccional ocurre en el servicio
            Factura factura = carroService.procesarCompra(carro, usuario);

            // 2. Limpiar el carro de la sesión después de una compra exitosa
            carroService.limpiarCarro(session);

            // 3. Pasar el ID de la factura como Flash Attribute
            redirectAttributes.addFlashAttribute("idFactura", factura.getIdFactura());
            redirectAttributes.addFlashAttribute("mensaje", "Compra procesada con éxito. Factura Nro: " + factura.getIdFactura());

            // 4. Redirigir a una ruta nueva para ver la factura
            System.out.println("Ver la Factura");
            return "redirect:/carro/verFactura?idFactura=" + factura.getIdFactura();

        } catch (RuntimeException e) {
            // Captura errores de stock, carro vacío o de la transacción
            redirectAttributes.addFlashAttribute("error", "Error al procesar la compra: " + e.getMessage());
            return "redirect:/carro/listado";
        }
    }

    // Nuevo método para mostrar la factura
    @GetMapping("/verFactura")
    public String verFactura(@RequestParam("idFactura") Integer idFactura, Model model) {
        if (idFactura == null) {
            return "redirect:/carro/listado";
        }

        Factura factura = facturaService.getFacturaConVentas(idFactura);
        model.addAttribute("factura", factura);
        return "carro/verFactura"; // Nombre del archivo Thymeleaf
    }
}
