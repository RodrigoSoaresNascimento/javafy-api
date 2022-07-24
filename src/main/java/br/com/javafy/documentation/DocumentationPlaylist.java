package br.com.javafy.documentation;

import br.com.javafy.anotations.MagiaResponse;
import br.com.javafy.dto.PageDTO;
import br.com.javafy.dto.playlist.PlayListCreate;
import br.com.javafy.dto.playlist.PlayListDTO;
import br.com.javafy.dto.playlist.PlaylistAddMusicaDTO;
import br.com.javafy.exceptions.PessoaNaoCadastradaException;
import br.com.javafy.exceptions.PlaylistException;
import br.com.javafy.exceptions.SpotifyException;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.SQLException;
import java.util.List;

@Validated
public interface DocumentationPlaylist {

    @Operation(summary = "Retorna uma playlist filtrada pelo id com suas musicas.")
    @MagiaResponse
    public ResponseEntity<PlayListDTO> getPlaylistWithIdWithMusics(
            Integer idPlaylist) throws PlaylistException, SpotifyException;


    @Operation(summary = "Retorna uma playlist filtrada pelo id sem suas musicas.")
    @MagiaResponse
    public ResponseEntity<PlayListDTO> getPlaylistWithIdWithNotMusics(
            Integer idPlaylist) throws PlaylistException;


    @Operation(summary = "Retorna uma lista de playlist paginada.")
    @MagiaResponse
    public PageDTO<PlayListDTO> getListPlayList(Integer pagina, Integer qtRegistro)
            throws PlaylistException;


    @Operation(summary = "Cria uma playlist.")
    @MagiaResponse
    public PlayListDTO create (@Valid @RequestBody PlayListCreate playListCreate,
                               @PathVariable Integer idUsuario) throws PlaylistException, PessoaNaoCadastradaException, SpotifyException;


    @Operation(summary = "Atualiza uma playlist.")
    @MagiaResponse
    public ResponseEntity<PlayListDTO> update (@PathVariable Integer idPlaylist,
                                               @Valid @RequestBody PlaylistAddMusicaDTO playlist)
            throws PlaylistException, SpotifyException;


    @Operation(summary = "Deleta uma playlist.")
    @MagiaResponse
    public void delete (@PathVariable Integer idPlayList) throws SQLException,
            PessoaNaoCadastradaException;

}
