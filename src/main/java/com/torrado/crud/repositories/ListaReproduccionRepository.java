package com.torrado.crud.repositories;

import com.torrado.crud.entities.ListaReproduccion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ListaReproduccionRepository extends JpaRepository<ListaReproduccion, Long> {

    Optional<ListaReproduccion> findByNombre(String nombre);

    boolean existsByNombre(String nombre);
}
