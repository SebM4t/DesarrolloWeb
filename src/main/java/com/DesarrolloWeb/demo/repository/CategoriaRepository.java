package com.DesarrolloWeb.demo.repository;
import com.DesarrolloWeb.demo.domain.Categoria;
import com.DesarrolloWeb.demo.domain.Placa;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
    
}
