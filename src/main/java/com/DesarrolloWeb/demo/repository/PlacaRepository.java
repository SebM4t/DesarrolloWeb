package com.DesarrolloWeb.demo.repository;

import com.DesarrolloWeb.demo.domain.Placa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PlacaRepository extends JpaRepository<Placa, Integer> {

//Buscar por categoria
    List<Placa> findByCategoriaIdCategoria(Integer idCategoria);

// Buscar por material
    List<Placa> findByMaterial(String material);

//Solo placas disponibles
    List<Placa> findByDisponibleTrue();

//Buscar disponibles por categoría
    List<Placa> findByDisponibleTrueAndCategoriaIdCategoria(Integer idCategoria);

}
