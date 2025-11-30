package com.torrado.crud.services;

import com.torrado.crud.entities.Cancion;
import com.torrado.crud.entities.ListaReproduccion;
import com.torrado.crud.repositories.ListaReproduccionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ListaReproduccionServiceImpl implements ListaReproduccionService{
    private final ListaReproduccionRepository listaRepository;

    @Autowired
    public ListaReproduccionServiceImpl(ListaReproduccionRepository listaRepository) {
        this.listaRepository = listaRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ListaReproduccion> findAll() {
        return listaRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ListaReproduccion> findById(Long id) {
        return listaRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ListaReproduccion> findByNombre(String nombre) {
        return listaRepository.findByNombre(nombre);
    }

    @Override
    @Transactional
    public ListaReproduccion save(ListaReproduccion lista) {
        // Establecer la relación bidireccional para cada canción
        if (lista.getCanciones() != null) {
            lista.getCanciones().forEach(cancion -> cancion.setLista(lista));
        }
        return listaRepository.save(lista);
    }

    @Override
    public void deleteById(Long id) {
        listaRepository.deleteById(id);
    }

    @Override
    public void deleteByNombre(String nombre) {
        listaRepository.findByNombre(nombre)
                .ifPresent(lista -> listaRepository.deleteById(lista.getId()));
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByNombre(String nombre) {
        return listaRepository.existsByNombre(nombre);
    }

    @Transactional
    public Optional<ListaReproduccion> agregarCancion(String nombreLista, Cancion cancion) {
        return findByNombre(nombreLista)
                .map(lista -> {
                    cancion.setLista(lista);
                    lista.getCanciones().add(cancion);
                    return save(lista);
                });
    }

    @Transactional
    public Optional<ListaReproduccion> eliminarCancion(String nombreLista, Long cancionId) {
        return findByNombre(nombreLista)
                .map(lista -> {
                    lista.getCanciones().removeIf(c -> c.getId().equals(cancionId));
                    return save(lista);
                });
    }
}
