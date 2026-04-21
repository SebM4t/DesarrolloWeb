package com.DesarrolloWeb.demo.repository;
import com.DesarrolloWeb.demo.domain.Cuenta;
import com.DesarrolloWeb.demo.domain.Usuario;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CuentaRepository extends JpaRepository<Cuenta, Integer> {
    Cuenta findByUsuario(Usuario usuario);
}
