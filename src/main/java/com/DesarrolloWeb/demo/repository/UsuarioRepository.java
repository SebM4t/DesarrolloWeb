package com.DesarrolloWeb.demo.repository;


import com.DesarrolloWeb.demo.domain.Usuario;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    
    public Optional<Usuario> findByUsernameAndActivoTrue(String username);
    
    public List<Usuario> findByActivoTrue();
    
    
    //se utiliza para verificar si hay un usuario con ese username o correo
    public boolean existsByUsernameOrCorreo(String username, String correo);
    
    //para encontrar un usuario que no se recuerda la clave...busca por username o correo
    public Optional<Usuario> findByUsernameOrCorreo(String username, String correo);

    //Se utiliza para el proceso de activacion final del usuario
    public Optional<Usuario> findByUsernameAndPassword(String username, String Password);
    
    
    public Optional<Usuario> findByUsername(String username);

    


}
