package com.DesarrolloWeb.demo.model;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "placa")
public class Placa {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

@Column(nullable = false, length = 150)
private String nombre;

@Column(columnDefinition = "TEXT")
private String descripcion;

@Column(nullable = false, length = 100)
private String material;

@Column(nullable = false, length = 50)
private String tamanio;

@Column(nullable = false, precision = 10, scale = 2)
private java.math.BigDecimal precio;

@Column(name = "imagen_nombre", length = 255)
private String imagenNombre;

@Column(nullable = false)
private Boolean disponible = true;

@ManyToOne
@JoinColumn(name = "categoria_id")
private Categoria categoria;

}
