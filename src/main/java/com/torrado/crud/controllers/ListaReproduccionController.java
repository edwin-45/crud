package com.torrado.crud.controllers;


import com.torrado.crud.entities.Cancion;
import com.torrado.crud.entities.ListaReproduccion;
import com.torrado.crud.services.ListaReproduccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/lists")
public class ListaReproduccionController {
    private final ListaReproduccionService listaService;

    @Autowired
    public ListaReproduccionController(ListaReproduccionService listaService) {
        this.listaService = listaService;
    }

    @PostMapping
    public ResponseEntity<ListaReproduccion> crearLista(@RequestBody ListaReproduccion lista) {
        if (lista.getNombre() == null || lista.getNombre().trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        if (listaService.existsByNombre(lista.getNombre())) {
            return ResponseEntity.badRequest().build();
        }

        ListaReproduccion nuevaLista = listaService.save(lista);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{listName}")
                .buildAndExpand(nuevaLista.getNombre())
                .toUri();

        return ResponseEntity.created(location).body(nuevaLista);
    }

    @GetMapping
    public ResponseEntity<List<ListaReproduccion>> obtenerTodasLasListas() {
        List<ListaReproduccion> listas = listaService.findAll();
        return ResponseEntity.ok(listas);
    }

    @GetMapping("/{listName}")
    public ResponseEntity<ListaReproduccion> obtenerListaPorNombre(@PathVariable String listName) {
        return listaService.findByNombre(listName)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{listName}")
    public ResponseEntity<Void> eliminarLista(@PathVariable String listName) {
        if (!listaService.existsByNombre(listName)) {
            return ResponseEntity.notFound().build();
        }

        listaService.deleteByNombre(listName);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{listName}/canciones")
    public ResponseEntity<ListaReproduccion> agregarCancion(
            @PathVariable String listName,
            @RequestBody Cancion cancion) {

        return listaService.agregarCancion(listName, cancion)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{listName}/canciones/{cancionId}")
    public ResponseEntity<ListaReproduccion> eliminarCancion(
            @PathVariable String listName,
            @PathVariable Long cancionId) {

        return listaService.eliminarCancion(listName, cancionId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
