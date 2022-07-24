package br.com.javafy.documentation;

import br.com.javafy.anotations.MagiaResponse;
import br.com.javafy.dto.spotify.musica.MusicaDTO;
import br.com.javafy.dto.spotify.musica.MusicaFullDTO;
import br.com.javafy.exceptions.PlaylistException;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface DocumentationMusica {

    @Operation(summary = "Exibir uma musica", description = "Exibir uma musica especificada por Id.")
    @MagiaResponse
    public MusicaFullDTO musicById(@PathVariable("id") String id) throws PlaylistException;

    @Operation(summary = "Retonar uma lista de musica pesquisada.")
    @MagiaResponse
    public List<MusicaDTO> searchMusic(String query) throws  PlaylistException;

}
