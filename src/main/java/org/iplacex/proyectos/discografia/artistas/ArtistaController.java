package org.iplacex.proyectos.discografia.artistas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class ArtistaController {

    @Autowired
    private IArtistaRepository artistaRepository;

    @PostMapping("/artista")
    public ResponseEntity<Artista> HandleInsertArtistaRequest(@RequestBody Artista artista) {
        Artista savedArtista = artistaRepository.save(artista);
        return new ResponseEntity<>(savedArtista, HttpStatus.CREATED);
    }

    @GetMapping("/artistas")
    public ResponseEntity<List<Artista>> HandleGetArtistasRequest() {
        List<Artista> artistas = artistaRepository.findAll();
        return new ResponseEntity<>(artistas, HttpStatus.OK);
    }

    @GetMapping("/artista/{id}")
    public ResponseEntity<Artista> HandleGetArtistaRequest(@PathVariable String id) {
        return artistaRepository.findById(id)
                .map(artista -> new ResponseEntity<>(artista, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/artista/{id}")
    public ResponseEntity<Artista> HandleUpdateArtistaRequest(@PathVariable String id, @RequestBody Artista artista) {
        if (!artistaRepository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        artista.setId(id);
        Artista updatedArtista = artistaRepository.save(artista);
        return new ResponseEntity<>(updatedArtista, HttpStatus.OK);
    }

    @DeleteMapping("/artista/{id}")
    public ResponseEntity<Void> HandleDeleteArtistaRequest(@PathVariable String id) {
        if (!artistaRepository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        artistaRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}