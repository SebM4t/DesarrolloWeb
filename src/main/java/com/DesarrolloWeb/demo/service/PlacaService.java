package com.DesarrolloWeb.demo.service;
import com.DesarrolloWeb.demo.model.Placa;
import com.DesarrolloWeb.demo.repository.PlacaRepository;
import org.springframework.stereotype.Service;
import java.util.List;

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

public List<Placa> listarPorCategoria(Long categoriaId) {
return placaRepository.findByDisponibleTrueAndCategoria_Id(categoriaId);
}

public List<Placa> listarPorMaterial(String material) {
return placaRepository.findByMaterial(material);
}

public Placa buscarPorId(Long id) {
return placaRepository.findById(id).orElse(null);
}

public Placa guardar(Placa placa) {
return placaRepository.save(placa);
}

public void eliminar(Long id) {
placaRepository.deleteById(id);
}
    
}
