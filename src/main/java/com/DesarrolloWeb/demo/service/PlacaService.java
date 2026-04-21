package com.DesarrolloWeb.demo.service;

import com.DesarrolloWeb.demo.domain.Placa;
import com.DesarrolloWeb.demo.repository.PlacaRepository;
import java.io.IOException;
import org.springframework.stereotype.Service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PlacaService {

    private final PlacaRepository placaRepository;
    @Autowired
    private FirebaseStorageService firebaseStorageService;


    public PlacaService(PlacaRepository placaRepository) {
        this.placaRepository = placaRepository;
    }

    public List<Placa> listarTodas() {
        return placaRepository.findAll();
    }

    public List<Placa> listarDisponibles() {
        return placaRepository.findByDisponibleTrue();
    }

    public List<Placa> listarPorCategoria(Integer idCategoria) {
        return placaRepository.findByDisponibleTrueAndCategoriaIdCategoria(idCategoria);
    }

    public List<Placa> listarPorMaterial(String material) {
        return placaRepository.findByMaterial(material);
    }

    public Placa buscarPorId(Integer idPlaca) {
        return placaRepository.findById(idPlaca).orElse(null);
    }

    public Placa guardar(Placa placa) {
        return placaRepository.save(placa);
    }

    public void eliminar(Integer idPlaca) {
        placaRepository.deleteById(idPlaca);
    }

    public void save(Placa placa, MultipartFile imagenFile) {
        if (placa.getIdPlaca() == null) {
            placaRepository.save(placa);
        }

        if (imagenFile != null && !imagenFile.isEmpty()) {
            try {
                String url = firebaseStorageService.uploadImage(imagenFile, "placas", placa.getIdPlaca());
                placa.setImagenNombre(url);
            } catch (IOException e) {
                System.out.println("Error al subir imagen: " + e.getMessage());
            }
        }
        placaRepository.save(placa);
    }
    public List<Placa> consultaJPQL(double precioInf, double precioSup){
        return placaRepository.consultaJPQL(precioInf, precioSup);
        
    }

    public List<Placa> consultaSQL(double precioInf, double precioSup){
        return placaRepository.consultaSQL(precioInf, precioSup);
    }

    public List<Placa> consultaNombreSQL(String nombre){
        return placaRepository.consultaNombreSQL(nombre);
    }
    
    

}
