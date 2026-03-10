package com.DesarrolloWeb.demo.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import lombok.Data;
import java.util.List;

@Data
@Entity
@Table(name = "categoria")
public class Categoria implements Serializable {

    private static final long serialVersionUID = 11;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_categoria")
    private Integer idCategoria;

    @NotNull
    @Size(max = 100)
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Size(max = 100)
    @Column(name = "descripcion", length = 100)
    private String descripcion;

    @OneToMany(mappedBy = "categoria")
    private List<Placa> placas;

}
