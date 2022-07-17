package br.com.javafy.controller;

import br.com.javafy.documentation.DocumentationArtista;
import br.com.javafy.dto.spotify.ArtistaDTO;
import br.com.javafy.service.ArtistaService;
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

    @GetMapping("/list")
    public List<ArtistaDTO> getArtists() {
        return service.getList();
    }

}
