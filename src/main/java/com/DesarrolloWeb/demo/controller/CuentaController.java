package com.DesarrolloWeb.demo.controller;

import com.DesarrolloWeb.demo.domain.Cuenta;
import com.DesarrolloWeb.demo.domain.Usuario;
import com.DesarrolloWeb.demo.service.CuentaService;
import com.DesarrolloWeb.demo.repository.UsuarioRepository;
import com.DesarrolloWeb.demo.repository.CuentaRepository;
import java.util.Locale;
import jakarta.validation.Valid;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/cuenta")
public class CuentaController {

    private final CuentaService cuentaService;
    private final UsuarioRepository usuarioRepository;
    private final CuentaRepository cuentaRepository;
    private final MessageSource messageSource;

    public CuentaController(CuentaService cuentaService, UsuarioRepository usuarioRepository, CuentaRepository cuentaRepository, MessageSource messageSource) {
        this.cuentaService = cuentaService;
        this.usuarioRepository = usuarioRepository;
        this.cuentaRepository = cuentaRepository;
        this.messageSource = messageSource;
    }

    @PostMapping("/guardar")
    public String guardar(@Valid Cuenta cuenta, RedirectAttributes redirectAttributes) {
        cuentaService.guardar(cuenta);
        redirectAttributes.addFlashAttribute("todoOk",
                messageSource.getMessage("mensaje.actualizado", null, Locale.getDefault()));
        return "redirect:/login";
    }

    @GetMapping
    public String verCuenta(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        Usuario usuario = usuarioRepository.findByUsername(username).orElse(null);
        if (usuario == null) {
            return "redirect:/login";
        }

        Cuenta cuenta = cuentaRepository.findByUsuario(usuario);
        if (cuenta == null) {
            cuenta = new Cuenta();
            cuenta.setUsuario(usuario);
        }
        model.addAttribute("cuenta", cuenta);
        return "cuenta/listado";
    }

}
