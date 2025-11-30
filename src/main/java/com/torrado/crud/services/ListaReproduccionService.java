package com.torrado.crud.services;

import com.torrado.crud.entities.Cancion;
import com.torrado.crud.entities.ListaReproduccion;

import java.util.List;
import java.util.Optional;

public interface ListaReproduccionService {

    List<ListaReproduccion> findAll();

    Optional<ListaReproduccion> findById(Long id);

    Optional<ListaReproduccion> findByNombre(String nombre);

    ListaReproduccion save(ListaReproduccion lista);

    void deleteById(Long id);

    void deleteByNombre(String nombre);

    boolean existsByNombre(String nombre);

    Optional<ListaReproduccion> agregarCancion(String nombreLista, Cancion cancion);

    Optional<ListaReproduccion> eliminarCancion(String nombreLista, Long cancionId);
}
