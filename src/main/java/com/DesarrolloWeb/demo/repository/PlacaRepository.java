package com.DesarrolloWeb.demo.repository;
import com.DesarrolloWeb.demo.model.Placa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface PlacaRepository extends JpaRepository<Placa, Long> {

//Buscar por categoria
List<Placa> findByCategoria_Id(Long categoriaId);

// Buscar por material
List<Placa> findByMaterial(String material);

//Solo placas disponibles
List<Placa> findByDisponibleTrue();

//Buscar disponibles por categoría
List<Placa> findByDisponibleTrueAndCategoria_Id(Long categoriaId);    
    
    
    
    
}
