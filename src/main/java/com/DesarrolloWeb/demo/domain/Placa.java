package com.DesarrolloWeb.demo.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

@Data
@Entity
@Table(name = "placa")
public class Placa implements Serializable {

    private static final long serialVersionUID = 1l;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_placa")
    private Integer idPlaca;

    @Column(nullable=false, length=50)
    @NotNull
    @Size(max=50)
    private String nombre;

    
    @Column(nullable=false, length=100) 
    @NotNull
    @Size(max=100)
    private String descripcion;

    @Column(nullable=false, length=100)
    @NotNull
    @Size(max=100)
    private String material;

    @Column(nullable=false, length=50)
    @NotNull
    @Size(max=50)
    private String tamanio;

    @Column(precision=12, scale=2)
    @NotNull(message="El precio no puede quedar vacío.")
    @DecimalMin(value="0.01", inclusive=true, message="El precio debe ser mayor a 0.")
    private BigDecimal precio;

    @Column(name = "imagen_nombre", length = 255)
    private String imagenNombre;

    @Column(nullable = false)
    private Boolean disponible;

    
    @ManyToOne
    @JoinColumn(name = "id_categoria")
    private Categoria categoria;

}
