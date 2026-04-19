package com.DesarrolloWeb.demo.service;

import com.DesarrolloWeb.demo.domain.Material;
import com.DesarrolloWeb.demo.repository.MaterialRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MaterialService {

    private final MaterialRepository materialRepository;

    public MaterialService(MaterialRepository materialRepository) {
        this.materialRepository = materialRepository;
    }

    public List<Material> listarTodos() {
        return materialRepository.findAllByOrderByNombreAsc();
    }

    public Material buscarPorId(Integer idMaterial) {
        return materialRepository.findById(idMaterial).orElse(null);
    }

    public Material guardar(Material material) {
        return materialRepository.save(material);
    }

    public void eliminar(Integer idMaterial) {
        materialRepository.deleteById(idMaterial);
    }
}