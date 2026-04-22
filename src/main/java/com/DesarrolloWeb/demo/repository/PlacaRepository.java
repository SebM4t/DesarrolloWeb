package com.DesarrolloWeb.demo.repository;

import com.DesarrolloWeb.demo.domain.Placa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface PlacaRepository extends JpaRepository<Placa, Integer> {

    List<Placa> findByCategoriaIdCategoria(Integer idCategoria);

    List<Placa> findByMaterial(String material);

    List<Placa> findByDisponibleTrue();

    List<Placa> findByDisponibleTrueAndCategoriaIdCategoria(Integer idCategoria);
    
    public List<Placa> findByPrecioBetweenOrderByNombre(double precioInf, double precioSup);
    
    public List<Placa> findByNombre(String nombre);
    
    @Query(value = "SELECT p FROM Placa p WHERE p.precio BETWEEN :precioInf and :precioSup ORDER by p.precio ASC")
    public List<Placa> consultaJPQL(double precioInf, double precioSup);

    @Query(nativeQuery = true, 
           value = "SELECT * FROM placa p WHERE p.precio BETWEEN :precioInf and :precioSup ORDER by p.precio ASC")
    public List<Placa> consultaSQL(double precioInf, double precioSup);

    @Query(nativeQuery = true,
           value = "SELECT * FROM placa p WHERE p.nombre LIKE CONCAT('%', :nombre, '%') ORDER BY p.nombre ASC")
    public List<Placa> consultaNombreSQL(String nombre);
    

}
