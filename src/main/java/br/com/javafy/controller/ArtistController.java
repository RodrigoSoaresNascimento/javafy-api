package br.com.javafy.controller;

import br.com.javafy.documentation.DocumentationArtista;

import br.com.javafy.dto.spotify.artista.ArtistaDTO;
import br.com.javafy.dto.spotify.musica.MusicaDTO;
import br.com.javafy.service.ArtistaService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ArtistaDTO artistById(@PathVariable("id") String id) {
        return service.artistById(id);
    }

    @GetMapping
    public List<ArtistaDTO> getArtists() {
        return service.getList();
    }

    @GetMapping("/{id}/{market}")
    public List<MusicaDTO> getTopTracksArtists(@PathVariable("id") String id, @PathVariable("market") String pais) throws JsonProcessingException {
        return service.searchArtist(id,pais);
    }

}
