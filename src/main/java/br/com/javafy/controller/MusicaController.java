package br.com.javafy.controller;

import br.com.javafy.documentation.DocumentationMusica;
import br.com.javafy.dto.spotify.genero.GeneroDTO;
import br.com.javafy.dto.spotify.musica.MusicaDTO;
import br.com.javafy.dto.spotify.musica.MusicaFullDTO;
import br.com.javafy.exceptions.PlaylistException;
import br.com.javafy.service.MusicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Validated
@RestController
@RequestMapping("/musica")
public class MusicaController implements DocumentationMusica {

    @Autowired
    private MusicaService musicaService;


    @GetMapping("/id-do-artista/{id}")
    public ResponseEntity<MusicaFullDTO> musicById(@PathVariable("id") String id) throws  PlaylistException {
        return ResponseEntity.ok(musicaService.musicById(id));
    }

    @GetMapping("/buscar-musica")
    public ResponseEntity<List<MusicaDTO>> searchMusic(String query) throws PlaylistException {
        return ResponseEntity.ok(musicaService.searchMusic(query));
    }

}