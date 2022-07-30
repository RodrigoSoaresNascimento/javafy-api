package br.com.javafy.documentation;

import br.com.javafy.anotations.MagiaResponse;
import br.com.javafy.dto.spotify.genero.GeneroDTO;
import br.com.javafy.dto.spotify.musica.MusicaDTO;
import br.com.javafy.dto.spotify.musica.MusicaFullDTO;
import br.com.javafy.exceptions.PlaylistException;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface DocumentationMusica {

    @Operation(summary = "Exibir uma musica", description = "Exibir uma musica especificada por Id.")
    @MagiaResponse
    ResponseEntity<MusicaFullDTO> musicById(@PathVariable("id") String id) throws PlaylistException;

    @Operation(summary = "Retonar uma lista de musica pesquisada.")
    @MagiaResponse
    ResponseEntity<List<MusicaDTO>> searchMusic(String query) throws  PlaylistException;

    @Operation(summary = "Retonar o genero da musica pesquisada", description = "genero é rock, samba etc")
    @MagiaResponse
    ResponseEntity<GeneroDTO> listaGenero() throws  PlaylistException;


}
