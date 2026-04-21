package com.DesarrolloWeb.demo.service;

import com.DesarrolloWeb.demo.domain.Soporte;
import org.springframework.stereotype.Service;
import java.util.List;
import com.DesarrolloWeb.demo.repository.SoporteRepository;

@Service
public class SoporteService {

    private final SoporteRepository soporteRepository;

    public SoporteService(SoporteRepository soporteRepository) {
        this.soporteRepository = soporteRepository;
    }
   

    public List<Soporte> listarTodas() {
        return soporteRepository.findAll();
    }

    public Soporte guardar(Soporte soporte) {
        return soporteRepository.save(soporte);
    }

    public void eliminar(Integer idSoporte) {
        soporteRepository.deleteById(idSoporte);
    }

}
