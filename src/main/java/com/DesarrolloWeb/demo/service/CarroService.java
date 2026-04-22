package com.DesarrolloWeb.demo.service;

import com.DesarrolloWeb.demo.domain.*;
import com.DesarrolloWeb.demo.repository.FacturaRepository;
import com.DesarrolloWeb.demo.repository.MaterialRepository;
import com.DesarrolloWeb.demo.repository.PlacaRepository;
import com.DesarrolloWeb.demo.repository.TamanioRepository;
import com.DesarrolloWeb.demo.repository.VentaRepository;

import jakarta.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CarroService {

    private static final String ATTRIBUTE_CARRO = "carro";

    private final PlacaRepository placaRepository;
    private final TamanioRepository tamanioRepository;
    private final MaterialRepository materialRepository;
    private final FacturaRepository facturaRepository;
    private final VentaRepository ventaRepository;

    public CarroService(PlacaRepository placaRepository, TamanioRepository tamanioRepository, MaterialRepository materialRepository, FacturaRepository facturaRepository, VentaRepository ventaRepository) {
        this.placaRepository = placaRepository;
        this.tamanioRepository = tamanioRepository;
        this.materialRepository = materialRepository;
        this.facturaRepository = facturaRepository;
        this.ventaRepository = ventaRepository;
    }

    public List<Item> obtenerCarro(HttpSession session) {
        @SuppressWarnings("unchecked")
        List<Item> carro = (List<Item>) session.getAttribute(ATTRIBUTE_CARRO);
        if (carro == null) {
            carro = new ArrayList<>();
        }
        return carro;
    }

    public void guardarCarro(HttpSession session, List<Item> carro) {
        session.setAttribute(ATTRIBUTE_CARRO, carro);
    }

    public void agregarCategoria(List<Item> carro, Integer idPlaca) {
        Placa placa = placaRepository.findById(idPlaca)
                .orElseThrow(() -> new RuntimeException("Placa no encontrada."));

        if (!placa.getDisponible()) {
            throw new RuntimeException("Lo sentimos, esta placa no está disponible actualmente.");
        }

        Optional<Item> itemExistente = carro.stream()
                .filter(i -> i.getPlaca() != null && i.getPlaca().getIdPlaca().equals(idPlaca))
                .findFirst();

        if (itemExistente.isPresent()) {
            Item item = itemExistente.get();
            item.setCantidad(item.getCantidad() + 1);
        } else {
            Item nuevoItem = new Item();
            nuevoItem.setPlaca(placa);
            nuevoItem.setPrecioHistorico(placa.getPrecio());
            nuevoItem.setCantidad(1);
            carro.add(nuevoItem);
        }
    }

    public void agregarDisenar(List<Item> carro, Integer idTamanio, Integer idMaterial) {
        Material material = materialRepository.findById(idMaterial)
                .orElseThrow(() -> new RuntimeException("Material no encontrado."));
        Tamanio tamanio = tamanioRepository.findById(idTamanio)
                .orElseThrow(() -> new RuntimeException("Tamaño no encontrado."));

        Optional<Item> itemExistente = carro.stream()
                .filter(i -> i.getMaterial() != null && i.getTamanio() != null
                && i.getMaterial().getIdMaterial().equals(idMaterial)
                && i.getTamanio().getIdTamanio().equals(idTamanio))
                .findFirst();

        if (!itemExistente.isPresent()) {
            Item nuevoItem = new Item();
            nuevoItem.setTamanio(tamanio);
            nuevoItem.setMaterial(material);

            BigDecimal precioFinalDisenar = material.getPrecio().add(tamanio.getPrecioAdicional());
            nuevoItem.setPrecioHistorico(precioFinalDisenar);
            nuevoItem.setCantidad(1);
            carro.add(nuevoItem);
        }
    }

    public Item buscarItemCategoria(List<Item> carro, Integer idPlaca) {
        if (carro == null) {
            return null;
        }

        return carro.stream()
                .filter(item -> item.getPlaca().getIdPlaca().equals(idPlaca))
                .findFirst() 
                .orElse(null);
    }

    public Item buscarItemDisenar(List<Item> carro, Integer idTamanio, Integer idMaterial) {
        if (carro == null) {
            return null;
        }
        return carro.stream()
                .filter(item -> item.getTamanio().getIdTamanio().equals(idTamanio)
                && item.getMaterial().getIdMaterial().equals(idMaterial))
                .findFirst()
                .orElse(null);
    }

    public void eliminarItemCategoria(List<Item> carro, Integer idPlaca) {
        carro.removeIf(item -> item.getPlaca().getIdPlaca().equals(idPlaca));
    }

    public void eliminarItemDisenar(List<Item> carro, Integer idTamanio, Integer idMaterial) {
        carro.removeIf(item -> item.getMaterial().getIdMaterial().equals(idMaterial)
                && item.getTamanio().getIdTamanio().equals(idTamanio));
    }

    public int contarUnidades(List<Item> carro) {
        if (carro == null) {
            return 0;
        }
        return carro.size();
    }

    public BigDecimal calcularTotal(List<Item> carro) {
        return carro.stream()
                .map(Item::getSubTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void limpiarCarro(HttpSession session) {
        List<Item> carro = obtenerCarro(session);
        if (carro != null) {
            carro.clear();
        }
        guardarCarro(session, carro);
    }

    @Transactional
    public Factura procesarCompra(List<Item> carro, Usuario usuario) {
        System.out.println("Se va a Procesar la Compra...");
        if (carro == null || carro.isEmpty()) {
            throw new RuntimeException("El carro está vacío para procesar la compra.");
        }

        Factura factura = new Factura();
        factura.setUsuario(usuario);
        factura.setFecha(java.time.LocalDateTime.now());
        factura.setTotal(calcularTotal(carro));
        factura.setEstado(EstadoFactura.Pagada);
        factura.setFechaCreacion(LocalDateTime.now());
        factura.setFechaModificacion(LocalDateTime.now());
        factura = facturaRepository.save(factura); 

        for (Item item : carro) {
            Venta venta = new Venta();
            venta.setFactura(factura);

            if (item.getPlaca() != null) {
                venta.setPlaca(item.getPlaca());
                venta.setCantidad(item.getCantidad() != null ? item.getCantidad() : 1);
                venta.setPrecioHistorico(item.getPrecioHistorico());
            } else {
                venta.setMaterial(item.getMaterial());
                venta.setTamanio(item.getTamanio());
                venta.setCantidad(item.getCantidad() != null ? item.getCantidad() : 1);

                BigDecimal precioMaterial = item.getMaterial() != null ? item.getMaterial().getPrecio() : BigDecimal.ZERO;
                BigDecimal precioTamanio = item.getTamanio() != null ? item.getTamanio().getPrecioAdicional() : BigDecimal.ZERO;
                venta.setPrecioHistorico(precioMaterial.add(precioTamanio));
            }

            venta.setFechaCreacion(LocalDateTime.now());
            venta.setFechaModificacion(LocalDateTime.now());

            ventaRepository.save(venta);
        }
        return factura;
    }
}
