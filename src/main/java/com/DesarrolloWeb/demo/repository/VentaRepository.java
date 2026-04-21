package com.DesarrolloWeb.demo.repository;

import com.DesarrolloWeb.demo.domain.Venta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VentaRepository extends JpaRepository<Venta, Integer>{
    
}
