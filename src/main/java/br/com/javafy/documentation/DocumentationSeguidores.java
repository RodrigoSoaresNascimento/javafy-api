package br.com.javafy.documentation;

import br.com.javafy.anotations.MagiaResponse;
import br.com.javafy.dto.usuario.UsuarioDTO;
import br.com.javafy.exceptions.PessoaException;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;

import java.sql.SQLException;
import java.util.List;

@Validated
public interface DocumentationSeguidores {

    @Operation(summary = "Retorna a lista de quem segue o usuario.")
    @MagiaResponse
    public List<UsuarioDTO> fromUser ()
            throws SQLException, PessoaException;

    @Operation(summary = "Retorna a lista de quem o usuario está seguindo.")
    @MagiaResponse
    public List<UsuarioDTO> toUser ()
            throws SQLException, PessoaException;

    @Operation(summary = "Seguir usuário.")
    @MagiaResponse
    public boolean seguirUser (@PathVariable Integer idSeguindo)
            throws SQLException, PessoaException;

    @Operation(summary = "Deixa de seguir um usuario.")
    @MagiaResponse
    public boolean deixarDeSeguirUsuario (@PathVariable Integer idSeguindo)
            throws SQLException, PessoaException;


}
