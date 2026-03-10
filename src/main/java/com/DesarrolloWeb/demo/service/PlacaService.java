package com.DesarrolloWeb.demo.service;

import com.DesarrolloWeb.demo.domain.Placa;
import com.DesarrolloWeb.demo.repository.PlacaRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PlacaService {

    private final PlacaRepository placaRepository;

    public PlacaService(PlacaRepository placaRepository) {
        this.placaRepository = placaRepository;
    }

    public List<Placa> listarTodas() {
        return placaRepository.findAll();
    }

    public List<Placa> listarDisponibles() {
        return placaRepository.findByDisponibleTrue();
    }

    public List<Placa> listarPorCategoria(Integer idCategoria) {
        return placaRepository.findByDisponibleTrueAndCategoriaIdCategoria(idCategoria);
    }

    public List<Placa> listarPorMaterial(String material) {
        return placaRepository.findByMaterial(material);
    }

    public Placa buscarPorId(Integer idPlaca) {
        return placaRepository.findById(idPlaca).orElse(null);
    }

    public Placa guardar(Placa placa) {
        return placaRepository.save(placa);
    }

    public void eliminar(Integer idPlaca) {
        placaRepository.deleteById(idPlaca);
    }

    public void save(Placa placa, MultipartFile imagenFile) {
        if (!imagenFile.isEmpty()) {

        }
        placaRepository.save(placa);
    }

}
