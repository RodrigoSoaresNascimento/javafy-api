package br.com.javafy.controller;

import br.com.javafy.documentation.DocumentationArtista;

import br.com.javafy.dto.spotify.artista.ArtistaDTO;
import br.com.javafy.dto.spotify.musica.MusicaDTO;
import br.com.javafy.exceptions.PlaylistException;
import br.com.javafy.exceptions.SpotifyException;
import br.com.javafy.service.ArtistaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/artista")
public class ArtistController implements DocumentationArtista {

    @Autowired
    private ArtistaService service;

    @GetMapping("/{id}")
    public ResponseEntity<ArtistaDTO> artistById(@PathVariable("id") String id)
            throws SpotifyException {
        return ResponseEntity.ok(service.artistById(id));
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<ArtistaDTO>> searchArtist(String query)
            throws SpotifyException, PlaylistException {
        return ResponseEntity.ok(service.getList(query));
    }

    @GetMapping("/{id}/{market}")
    public  ResponseEntity<List<MusicaDTO>> getTopTracksArtists(@PathVariable("id") String id, @PathVariable("market") String pais) throws SpotifyException {
        return ResponseEntity.ok(service.searchArtist(id,pais));
    }

}
