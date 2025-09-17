package org.iplacex.proyectos.discografia.discos;

import org.iplacex.proyectos.discografia.artistas.IArtistaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class DiscoController {

    @Autowired
    private IDiscoRepository discoRepository;

    @Autowired
    private IArtistaRepository artistaRepository;

    @PostMapping("/disco")
    public ResponseEntity<Object> HandlePostDiscoRequest(@RequestBody Disco disco) {
        
        if (!artistaRepository.existsById(disco.getIdArtista())) {
            return new ResponseEntity<>("Artista not found", HttpStatus.NOT_FOUND);
        }
        Disco savedDisco = discoRepository.save(disco);
        return new ResponseEntity<>(savedDisco, HttpStatus.CREATED);
    }

    @GetMapping("/discos")
    public ResponseEntity<List<Disco>> HandleGetDiscosRequest() {
        List<Disco> discos = discoRepository.findAll();
        return new ResponseEntity<>(discos, HttpStatus.OK);
    }

    @GetMapping("/disco/{id}")
    public ResponseEntity<Disco> HandleGetDiscoRequest(@PathVariable String id) {
        return discoRepository.findById(id)
                .map(disco -> new ResponseEntity<>(disco, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/artista/{idArtista}/discos")
    public ResponseEntity<List<Disco>> HandleGetDiscosByArtistaRequest(@PathVariable String idArtista) {
        List<Disco> discos = discoRepository.findDiscosByIdArtista(idArtista);
        return new ResponseEntity<>(discos, HttpStatus.OK);
    }

   

}