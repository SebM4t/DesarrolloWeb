package com.DesarrolloWeb.demo.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import lombok.Data;
import java.util.List;

@Data
@Entity
@Table(name = "soporte")
public class Soporte implements Serializable {

    private static final long serialVersionUID = 11;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_soporte")
    private Integer idSoporte;

    @NotNull
    @Size(max = 60)
    @Column(name = "nombre_completo", nullable = false, length = 60)
    private String nombreCompleto;

    @NotNull
    @Size(max = 25)
    @Column(name = "telefono", nullable = false, length = 25)
    private String telefono;
    
    @NotNull
    @Size(max = 25)
    @Column(name = "correo", nullable = false, length = 25)
    private String correo;
    
    @NotNull
    @Size(max = 250)
    @Column(name = "mensaje_soporte", nullable = false, length = 250)
    private String mensajeSoporte;

}
