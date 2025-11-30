package com.torrado.crud.services;

import com.torrado.crud.entities.Cancion;
import com.torrado.crud.repositories.CancionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CancionServiceImpl implements CancionService{

    private final CancionRepository cancionRepository;

    @Autowired
    public CancionServiceImpl(CancionRepository cancionRepository) {
        this.cancionRepository = cancionRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Cancion> findAll() {
        return cancionRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Cancion> findById(Long id) {
        return cancionRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Cancion> findByListaId(Long listaId) {
        return cancionRepository.findByListaId(listaId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Cancion> findByArtista(String artista) {
        return cancionRepository.findByArtista(artista);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Cancion> findByGenero(String genero) {
        return cancionRepository.findByGenero(genero);
    }

    @Override
    public Cancion save(Cancion cancion) {
        return cancionRepository.save(cancion);
    }

    @Override
    public void deleteById(Long id) {
        cancionRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return cancionRepository.existsById(id);
    }
}
