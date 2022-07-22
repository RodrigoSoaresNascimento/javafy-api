package br.com.javafy.documentation;

import br.com.javafy.anotations.MagiaResponse;
import br.com.javafy.dto.ComentarioDTO;
import br.com.javafy.dto.UsuarioCreateDTO;
import br.com.javafy.dto.UsuarioDTO;
import br.com.javafy.exceptions.ComentarioNaoCadastradoException;
import br.com.javafy.exceptions.PessoaNaoCadastradaException;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.sql.SQLException;
import java.util.List;

@Validated
public interface DocumentationComentario {
    @Operation(summary = "Procura um comentario pelo seu ID")
    @MagiaResponse
    public ResponseEntity<ComentarioDTO> findById(@PathVariable("idUser") Integer idUser)
            throws SQLException, PessoaNaoCadastradaException, ComentarioNaoCadastradoException;

    @Operation(summary = "Lista todos os comentarios pelo seu ID")
    @MagiaResponse
    public ResponseEntity<List<ComentarioDTO>> list() throws SQLException;

    @Operation(summary = "Cria um perfil de comentario")
    @MagiaResponse
    public ResponseEntity<ComentarioDTO> create(@PathVariable("idUser")Integer idUser, @PathVariable("idPlaylist")Integer idPlaylist,
                                                @Valid @RequestBody ComentarioDTO comentario)throws SQLException;

    @Operation(summary = "Atualiza um comentario pelo seu ID")
    @MagiaResponse
    public ResponseEntity<ComentarioDTO> update(@PathVariable("idComentario") Integer id
            , @Valid @RequestBody ComentarioDTO comentario) throws PessoaNaoCadastradaException,
            SQLException;

    @Operation(summary = "Apaga comentario pelo seu ID")
    @MagiaResponse
    public void delete(@PathVariable("idComentario") Integer id) throws PessoaNaoCadastradaException, SQLException;

}
