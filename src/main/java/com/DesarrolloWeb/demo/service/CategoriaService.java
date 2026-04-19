package com.DesarrolloWeb.demo.service;

import com.DesarrolloWeb.demo.domain.Categoria;
import org.springframework.stereotype.Service;
import java.util.List;
import com.DesarrolloWeb.demo.repository.CategoriaRepository;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public List<Categoria> listarTodas() {
        return categoriaRepository.findAll();
    }

    public Categoria buscarPorId(Integer idCategoria) {
        return categoriaRepository.findById(idCategoria).orElse(null);
    }

    public Categoria guardar(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    public void eliminar(Integer idCategoria) {
        categoriaRepository.deleteById(idCategoria);
    }

}
