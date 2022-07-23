package br.com.javafy.controller;

import br.com.javafy.dto.ComentarioPlaylistRelatorioDTO;
import br.com.javafy.service.ComentarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/relatorios")
@Validated
public class RelatorioController {

    @Autowired
    ComentarioService service;

    @GetMapping("/comentarios-playlist")
    public List<ComentarioPlaylistRelatorioDTO> relatorioComentarioPlaylist(@RequestParam(required = false) Integer idUsuario){
        return service.relatorioComentarioPlaylist(idUsuario);
    }

}
