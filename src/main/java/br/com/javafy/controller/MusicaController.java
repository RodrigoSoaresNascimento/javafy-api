package br.com.javafy.controller;

import br.com.javafy.documentation.DocumentationMusica;
import br.com.javafy.service.dto.spotify.MusicaDTO;
import br.com.javafy.service.MusicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Validated
@RestController
@RequestMapping("/musica")
public class MusicaController implements DocumentationMusica {

    @Autowired
    private MusicaService musicaService;

    @GetMapping("/{id}")
    public MusicaDTO musicById(@PathVariable("id") String id) {
        return musicaService.musicById(id);
    }

    @GetMapping("/list")
    public List<MusicaDTO> getList() {
        return musicaService.getList();
    }

    @GetMapping
    public Map searchMusic() {
        return musicaService.searchMusic();
    }

}