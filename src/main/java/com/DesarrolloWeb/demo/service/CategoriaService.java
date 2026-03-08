package com.DesarrolloWeb.demo.service;
import com.DesarrolloWeb.demo.model.Categoria;
import com.DesarrolloWeb.demo.repository.CategoriaRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CategoriaService {
    
private final CategoriaRepository categoriaRepository;

public CategoriaService(CategoriaRepository categoriaRepository) {
this.categoriaRepository = categoriaRepository;
}

public List<Categoria> listarTodas() {
return categoriaRepository.findAll();
}

public Categoria buscarPorId(Long id) {
return categoriaRepository.findById(id).orElse(null);
}

public Categoria guardar(Categoria categoria) {
return categoriaRepository.save(categoria);
}

public void eliminar(Long id) {
categoriaRepository.deleteById(id);
    }

}
