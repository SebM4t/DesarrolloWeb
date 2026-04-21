package com.DesarrolloWeb.demo.repository;

import com.DesarrolloWeb.demo.domain.Factura;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FacturaRepository extends JpaRepository<Factura, Integer>{
    // Consulta para cargar la Factura, su Usuario, sus Ventas y los Placas de esas ventas.
    @Query("SELECT f FROM Factura f " +
           "LEFT JOIN FETCH f.usuario u " +       // Carga inmediata del Usuario
           "LEFT JOIN FETCH f.ventas v " +        // Carga inmediata de la lista de Ventas
           "LEFT JOIN FETCH v.placa p " +      // Carga inmediata del Placa en cada Venta
           "WHERE f.idFactura = :idFactura")
    Optional<Factura> findByIdFacturaConDetalle(@Param("idFactura") Integer idFactura);
}