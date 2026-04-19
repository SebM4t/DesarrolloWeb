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
@Table(name = "tamanio")
public class Tamanio implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tamanio")
    private Integer idTamanio;

    @NotNull
    @Size(max = 50)
    @Column(nullable = false, length = 50)
    private String dimensiones;

    @NotNull(message = "El precio adicional no puede quedar vacío.")
    @DecimalMin(value = "0.00", inclusive = true, message = "El precio adicional no puede ser negativo.")
    @Column(name = "precio_adicional", precision = 12, scale = 2)
    private BigDecimal precioAdicional;
}