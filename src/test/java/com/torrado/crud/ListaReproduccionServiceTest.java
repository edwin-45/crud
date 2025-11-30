package com.torrado.crud;

import com.torrado.crud.entities.Cancion;
import com.torrado.crud.entities.ListaReproduccion;
import com.torrado.crud.repositories.ListaReproduccionRepository;
import com.torrado.crud.services.ListaReproduccionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ListaReproduccionServiceTest {

    @Mock
    private ListaReproduccionRepository repository;

    @InjectMocks
    private ListaReproduccionServiceImpl service;

    private ListaReproduccion lista;
    private Cancion cancion;

    @BeforeEach
    void setUp() {
        lista = new ListaReproduccion();
        lista.setId(1L);
        lista.setNombre("Mi Lista");
        lista.setDescripcion("Descripción");
        lista.setCanciones(new ArrayList<>());

        cancion = new Cancion();
        cancion.setId(1L);
        cancion.setTitulo("Canción de Prueba");
        cancion.setArtista("Artista de Prueba");
        cancion.setAlbum("Album de Prueba");
        cancion.setGenero("Rock");
    }

    @Test
    void deberiaObtenerTodasLasListas() {
        List<ListaReproduccion> listas = List.of(lista);
        when(repository.findAll()).thenReturn(listas);

        List<ListaReproduccion> resultado = service.findAll();

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getNombre()).isEqualTo("Mi Lista");
        verify(repository, times(1)).findAll();
    }

    @Test
    void deberiaEncontrarListaPorNombre() {
        when(repository.findByNombre("Mi Lista")).thenReturn(Optional.of(lista));

        Optional<ListaReproduccion> resultado = service.findByNombre("Mi Lista");

        assertThat(resultado).isPresent();
        assertThat(resultado.get().getNombre()).isEqualTo("Mi Lista");
        verify(repository, times(1)).findByNombre("Mi Lista");
    }

    @Test
    void deberiaGuardarLista() {
        when(repository.save(any(ListaReproduccion.class))).thenReturn(lista);

        ListaReproduccion resultado = service.save(lista);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getNombre()).isEqualTo("Mi Lista");
        verify(repository, times(1)).save(lista);
    }

    @Test
    void deberiaAgregarCancionALista() {
        when(repository.findByNombre("Mi Lista")).thenReturn(Optional.of(lista));
        when(repository.save(any(ListaReproduccion.class))).thenReturn(lista);

        Optional<ListaReproduccion> resultado = service.agregarCancion("Mi Lista", cancion);

        assertThat(resultado).isPresent();
        assertThat(lista.getCanciones()).hasSize(1);
        assertThat(lista.getCanciones().get(0).getTitulo()).isEqualTo("Canción de Prueba");
        verify(repository, times(1)).findByNombre("Mi Lista");
        verify(repository, times(1)).save(lista);
    }

    @Test
    void deberiaEliminarCancionDeLista() {
        lista.getCanciones().add(cancion);
        when(repository.findByNombre("Mi Lista")).thenReturn(Optional.of(lista));
        when(repository.save(any(ListaReproduccion.class))).thenReturn(lista);

        Optional<ListaReproduccion> resultado = service.eliminarCancion("Mi Lista", 1L);

        assertThat(resultado).isPresent();
        assertThat(lista.getCanciones()).isEmpty();
        verify(repository, times(1)).findByNombre("Mi Lista");
        verify(repository, times(1)).save(lista);
    }


    @Test
    void deberiaVerificarExistenciaDeLista() {
        when(repository.existsByNombre("Mi Lista")).thenReturn(true);

        boolean existe = service.existsByNombre("Mi Lista");

        assertThat(existe).isTrue();
        verify(repository, times(1)).existsByNombre("Mi Lista");
    }

    @Test
    void noDeberiaAgregarCancionAListaInexistente() {
        when(repository.findByNombre(anyString())).thenReturn(Optional.empty());

        Optional<ListaReproduccion> resultado = service.agregarCancion("Lista Inexistente", cancion);

        assertThat(resultado).isEmpty();
        verify(repository, never()).save(any());
    }
}
