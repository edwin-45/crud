package com.torrado.crud.repositories;

import com.torrado.crud.entities.Cancion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CancionRepository extends JpaRepository<Cancion, Long> {

    List<Cancion> findByListaId(Long listaId);

    List<Cancion> findByArtista(String artista);

    List<Cancion> findByGenero(String genero);
}
