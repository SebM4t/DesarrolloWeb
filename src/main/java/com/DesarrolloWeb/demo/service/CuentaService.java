package com.DesarrolloWeb.demo.service;

import com.DesarrolloWeb.demo.domain.Cuenta;
import com.DesarrolloWeb.demo.domain.Usuario;
import org.springframework.stereotype.Service;
import java.util.List;
import com.DesarrolloWeb.demo.repository.CuentaRepository;
import com.DesarrolloWeb.demo.repository.UsuarioRepository;

@Service
public class CuentaService {

    private final CuentaRepository cuentaRepository;
    private final UsuarioRepository usuarioRepository;

    public CuentaService(CuentaRepository cuentaRepository, UsuarioRepository usuarioRepository) {
        this.cuentaRepository = cuentaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<Cuenta> listarTodas() {
        return cuentaRepository.findAll();
    }

    public Cuenta guardar(Cuenta cuenta) {
        Usuario usuarioFormulario = cuenta.getUsuario();

        Usuario usuarioExistente = usuarioRepository.findById(usuarioFormulario.getIdUsuario())
                .orElseThrow();

        usuarioExistente.setNombre(usuarioFormulario.getNombre());
        usuarioExistente.setApellidos(usuarioFormulario.getApellidos());
        usuarioExistente.setTelefono(usuarioFormulario.getTelefono());
        usuarioExistente.setCorreo(usuarioFormulario.getCorreo());
        usuarioExistente.setUsername(usuarioFormulario.getUsername());

        usuarioRepository.save(usuarioExistente);

        cuenta.setUsuario(usuarioExistente);

        return cuentaRepository.save(cuenta);
    }

    public void eliminar(Integer idCuenta) {
        cuentaRepository.deleteById(idCuenta);
    }

}
