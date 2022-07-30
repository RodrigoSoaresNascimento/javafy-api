package br.com.javafy.controller;

import br.com.javafy.documentation.DocumentationRelatorio;
import br.com.javafy.dto.comentario.ComentarioPlaylistRelatorioDTO;
import br.com.javafy.dto.usuario.UsuarioRelatorioDTO;
import br.com.javafy.service.ComentarioService;
import br.com.javafy.service.UsuarioService;
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
public class RelatorioController implements DocumentationRelatorio {

    @Autowired
    private ComentarioService comentarioService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/comentarios-playlist")
    public List<ComentarioPlaylistRelatorioDTO>
    relatorioComentarioPlaylist(){
        return comentarioService.relatorioComentarioPlaylist();
    }

    @GetMapping("/relatorio-playlists")
    public List<UsuarioRelatorioDTO> relatorioPlayList(){
        return usuarioService.relatorio();
    }

}
