package com.DesarrolloWeb.demo.service;

import com.DesarrolloWeb.demo.domain.Tamanio;
import com.DesarrolloWeb.demo.repository.TamanioRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TamanioService {

    private final TamanioRepository tamanioRepository;

    public TamanioService(TamanioRepository tamanioRepository) {
        this.tamanioRepository = tamanioRepository;
    }

    public List<Tamanio> listarTodos() {
        return tamanioRepository.findAllByOrderByPrecioAdicionalAsc();
    }

    public Tamanio buscarPorId(Integer idTamanio) {
        return tamanioRepository.findById(idTamanio).orElse(null);
    }

    public Tamanio guardar(Tamanio tamanio) {
        return tamanioRepository.save(tamanio);
    }

    public void eliminar(Integer idTamanio) {
        tamanioRepository.deleteById(idTamanio);
    }
}