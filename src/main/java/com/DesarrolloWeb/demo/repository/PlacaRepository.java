package com.DesarrolloWeb.demo.repository;

import com.DesarrolloWeb.demo.domain.Placa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import org.springframework.data.jpa.repository.Query;

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
    
    // Filtro por rango de precio
    public List<Placa> findByPrecioBetweenOrderByNombre(double precioInf, double precioSup);
    
    public List<Placa> findByNombre(String nombre);
    
    @Query(value = "SELECT p FROM Placa p WHERE p.precio BETWEEN :precioInf and :precioSup ORDER by p.precio ASC")
    public List<Placa> consultaJPQL(double precioInf, double precioSup);

    // 2. Consulta SQL Nativa por rango de precio
    @Query(nativeQuery = true, 
           value = "SELECT * FROM placa p WHERE p.precio BETWEEN :precioInf and :precioSup ORDER by p.precio ASC")
    public List<Placa> consultaSQL(double precioInf, double precioSup);

    // 3. Consulta por Nombre (usando LIKE)
    @Query(nativeQuery = true,
           value = "SELECT * FROM placa p WHERE p.nombre LIKE CONCAT('%', :nombre, '%') ORDER BY p.nombre ASC")
    public List<Placa> consultaNombreSQL(String nombre);
    

}
