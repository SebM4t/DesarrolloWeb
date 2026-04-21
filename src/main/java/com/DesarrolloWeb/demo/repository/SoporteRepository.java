package com.DesarrolloWeb.demo.repository;
import com.DesarrolloWeb.demo.domain.Soporte;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SoporteRepository extends JpaRepository<Soporte, Integer> {
    
}
