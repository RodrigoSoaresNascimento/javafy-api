package br.com.javafy.controller;

import br.com.javafy.documentation.DocumentationMusica;
import br.com.javafy.dto.spotify.MusicaDTO;
import br.com.javafy.dto.spotify.MusicaFullDTO;
import br.com.javafy.dto.spotify.QueryDTO;
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
    public MusicaFullDTO musicById(@PathVariable("id") String id) {
        return musicaService.musicById(id);
    }

    @GetMapping("/list")
    public List<MusicaFullDTO> getList() {
        return musicaService.getList();
    }

    @GetMapping("/buscar")
    public List<MusicaDTO> searchMusic(@RequestBody QueryDTO query) throws IOException {
        System.out.println("QUERY");
        return musicaService.searchMusic(query);
    }

}