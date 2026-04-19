package com.DesarrolloWeb.demo.repository;

import com.DesarrolloWeb.demo.domain.Tamanio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TamanioRepository extends JpaRepository<Tamanio, Integer> {
    
    List<Tamanio> findByDimensiones(String dimensiones);
    
    List<Tamanio> findAllByOrderByPrecioAdicionalAsc();
}