package br.com.javafy.controller;

import br.com.javafy.documentation.DocumentationMusica;
import br.com.javafy.dto.spotify.musica.MusicaDTO;
import br.com.javafy.dto.spotify.musica.MusicaFullDTO;
import br.com.javafy.exceptions.SpotifyException;
import br.com.javafy.service.MusicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Validated
@RestController
@RequestMapping("/musica")
public class MusicaController implements DocumentationMusica {

    @Autowired
    private MusicaService musicaService;

    @GetMapping("/{id}")
    public MusicaFullDTO musicById(@PathVariable("id") String id) throws SpotifyException {
        return musicaService.musicById(id);
    }

    //todo -> retirar o requestbody e adicionar uma string
    @GetMapping("/buscar")
    public List<MusicaDTO> searchMusic(String query) throws IOException, SpotifyException {
        System.out.println("QUERY");
        return musicaService.searchMusic(query);
    }

}