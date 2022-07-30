package br.com.javafy.documentation;

import br.com.javafy.anotations.MagiaResponse;
import br.com.javafy.dto.PageDTO;
import br.com.javafy.dto.comentario.ComentarioCreateDTO;
import br.com.javafy.dto.comentario.ComentarioDTO;
import br.com.javafy.exceptions.ComentarioNaoCadastradoException;
import br.com.javafy.exceptions.PessoaException;
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
    public ResponseEntity<ComentarioDTO> findById(@PathVariable("idComentario") Integer idUser)
            throws SQLException, PessoaException, ComentarioNaoCadastradoException;

    @Operation(summary = "Lista todos os comentarios pelo seu ID")
    @MagiaResponse
    public ResponseEntity<List<ComentarioDTO>> list();

    @Operation(summary = "Cria um perfil de comentario")
    @MagiaResponse
    public ResponseEntity<ComentarioDTO> create(@PathVariable("idPlaylist")Integer idPlaylist,
                                                @Valid @RequestBody ComentarioCreateDTO comentarioCreateDTO)
            throws ComentarioNaoCadastradoException;

    @Operation(summary = "Atualiza um comentario pelo seu ID")
    @MagiaResponse
    public ResponseEntity<ComentarioDTO> update(@PathVariable("idComentario") Integer idComentario
            , @Valid @RequestBody ComentarioCreateDTO comentarioAtualizar)
            throws ComentarioNaoCadastradoException, PessoaException;

    @Operation(summary = "Apaga comentario pelo seu ID")
    @MagiaResponse
    public void delete(@PathVariable("idComentario") Integer id)
            throws PessoaException, SQLException, ComentarioNaoCadastradoException;

    @Operation(summary = "Tras comentarios paginados")
    @MagiaResponse
    ResponseEntity<PageDTO<ComentarioDTO>>  comentarios (Integer pagina, Integer qtRegistro);


}
