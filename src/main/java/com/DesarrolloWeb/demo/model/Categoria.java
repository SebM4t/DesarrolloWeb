package com.DesarrolloWeb.demo.model;
import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
@Table(name = "categoria")
public class Categoria {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

@Column(nullable = false, length = 100)
private String nombre;

@Column(columnDefinition = "TEXT")
private String descripcion;

@OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL)
private List<Placa> placas;

}
