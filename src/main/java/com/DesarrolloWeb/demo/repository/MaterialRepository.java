package com.DesarrolloWeb.demo.repository;

import com.DesarrolloWeb.demo.domain.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Integer> {
    
    List<Material> findByNombre(String nombre);
    
    List<Material> findAllByOrderByNombreAsc();
}