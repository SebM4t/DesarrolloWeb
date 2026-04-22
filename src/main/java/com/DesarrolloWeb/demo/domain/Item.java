package com.DesarrolloWeb.demo.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item implements Serializable {

    private static final long serialVersionUID = 1L;

    private Placa placa;
    private Material material;
    private Tamanio tamanio;
    private Integer cantidad;

    private BigDecimal precioHistorico;

    public BigDecimal getSubTotal() {
        return precioHistorico != null ? precioHistorico.multiply(BigDecimal.valueOf(cantidad)) : BigDecimal.ZERO;
    }
}
