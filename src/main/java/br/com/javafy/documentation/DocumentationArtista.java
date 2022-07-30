package br.com.javafy.documentation;

import br.com.javafy.anotations.MagiaResponse;
import br.com.javafy.dto.spotify.artista.ArtistaDTO;
import br.com.javafy.dto.spotify.musica.MusicaDTO;
import br.com.javafy.exceptions.PlaylistException;
import br.com.javafy.exceptions.SpotifyException;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface DocumentationArtista {

    @Operation(summary = "Exibir um artista",
            description = "Exibir um artista especificado por Id.")
    @MagiaResponse
    ResponseEntity<ArtistaDTO> artistById(@PathVariable("id") String id)
            throws SpotifyException;

    @Operation(summary = "Faz uma query no artista.",
            description = "Exibir uma lista de artista pesquisada.")
    @MagiaResponse
    ResponseEntity<List<ArtistaDTO>> searchArtist(String query)
            throws SpotifyException, PlaylistException;

    @Operation(summary = "Seleciona as musicas mais populares de um artista",
            description = "Exibir uma lista de musicas de um artista em um pais")
    @MagiaResponse
    ResponseEntity<List<MusicaDTO>> getTopTracksArtists (String id, String pais)
            throws SpotifyException, PlaylistException;
}
