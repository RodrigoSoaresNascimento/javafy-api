package br.com.javafy.documentation;

import br.com.javafy.anotations.MagiaResponse;
import br.com.javafy.dto.playlist.PlayListCreate;
import br.com.javafy.dto.playlist.PlayListDTO;
import br.com.javafy.dto.playlist.PlayListUpdate;
import br.com.javafy.exceptions.PessoaNaoCadastradaException;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.SQLException;

@Validated
public interface DocumentationPlaylist {

    @Operation(summary = "Retorna uma playlist.")
    @MagiaResponse
    public ResponseEntity<PlayListDTO> getPlayList (@Valid @PathVariable Integer idPlaylist)
            throws SQLException, PessoaNaoCadastradaException;

    @Operation(summary = "Cria uma playlist.")
    @MagiaResponse
    public PlayListDTO create (@Valid @RequestBody PlayListCreate playListCreate,
                               @PathVariable Integer idUsuario) throws SQLException,
            PessoaNaoCadastradaException;

    @Operation(summary = "Atualiza uma playlist.")
    @MagiaResponse
    public PlayListUpdate update (@Valid @RequestBody PlayListCreate playListCreate,
                                  @PathVariable Integer idPlaylist )
            throws SQLException;

    @Operation(summary = "Deleta uma playlist.")
    @MagiaResponse
    public void delete (@PathVariable Integer idPlayList) throws SQLException,
            PessoaNaoCadastradaException;

}
