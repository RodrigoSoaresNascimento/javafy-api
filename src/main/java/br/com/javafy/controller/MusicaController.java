package br.com.javafy.controller;

import br.com.javafy.documentation.DocumentationMusica;
import br.com.javafy.dto.spotify.genero.GeneroDTO;
import br.com.javafy.dto.spotify.musica.MusicaDTO;
import br.com.javafy.dto.spotify.musica.MusicaFullDTO;
import br.com.javafy.exceptions.PlaylistException;
import br.com.javafy.service.MusicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/musica")
public class MusicaController implements DocumentationMusica {

    @Autowired
    private MusicaService musicaService;


    @GetMapping("/id-do-artista/{id}")
    public MusicaFullDTO musicById(@PathVariable("id") String id) throws  PlaylistException {
        return musicaService.musicById(id);
    }

    @GetMapping("/buscar-musica")
    public List<MusicaDTO> searchMusic(String query) throws PlaylistException {
        return musicaService.searchMusic(query);
    }

    @GetMapping("/buscar-genero")
    public GeneroDTO listaGenero() throws PlaylistException {
        return musicaService.listarGenero();
    }
}