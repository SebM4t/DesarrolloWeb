package com.DesarrolloWeb.demo.repository;

import com.DesarrolloWeb.demo.domain.Constante;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConstanteRepository extends JpaRepository<Constante,Integer> {
    
    public Optional<Constante> findByAtributo(String atributo);
    
}