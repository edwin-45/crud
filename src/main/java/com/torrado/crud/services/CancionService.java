package com.torrado.crud.services;

import com.torrado.crud.entities.Cancion;

import java.util.List;
import java.util.Optional;

public interface CancionService {

    List<Cancion> findAll();

    Optional<Cancion> findById(Long id);

    List<Cancion> findByListaId(Long listaId);

    List<Cancion> findByArtista(String artista);

    List<Cancion> findByGenero(String genero);

    Cancion save(Cancion cancion);

    void deleteById(Long id);

    boolean existsById(Long id);
}
