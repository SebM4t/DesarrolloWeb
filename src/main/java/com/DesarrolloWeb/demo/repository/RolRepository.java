package com.DesarrolloWeb.demo.repository;


import com.DesarrolloWeb.demo.domain.Rol;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolRepository extends JpaRepository<Rol, Integer> {

    //se utiliza en el proceso de crear de registrar un nuevo usuario... por defecto es user
    public Optional<Rol> findByRol(String rol);

}
