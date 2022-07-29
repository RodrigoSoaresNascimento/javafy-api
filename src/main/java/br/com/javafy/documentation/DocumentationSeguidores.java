package br.com.javafy.documentation;

import br.com.javafy.anotations.MagiaResponse;
import br.com.javafy.dto.usuario.UsuarioDTO;
import br.com.javafy.exceptions.PessoaNaoCadastradaException;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;

import java.sql.SQLException;
import java.util.List;

@Validated
public interface DocumentationSeguidores {

    @Operation(summary = "Retorna a lista de quem segue o usuario.")
    @MagiaResponse
    public List<UsuarioDTO> fromUser (@PathVariable Integer idUser)
            throws SQLException, PessoaNaoCadastradaException;

    @Operation(summary = "Retorna a lista de quem o usuario está seguindo.")
    @MagiaResponse
    public List<UsuarioDTO> toUser (@PathVariable Integer idUser)
            throws SQLException, PessoaNaoCadastradaException;

    @Operation(summary = "Seguir usuário.")
    @MagiaResponse
    public Boolean seguirUser (@PathVariable Integer meuId,
                                   @PathVariable Integer idSeguindo)
            throws SQLException, PessoaNaoCadastradaException;

    @Operation(summary = "Deixa de seguir um usuario.")
    @MagiaResponse
    public void deixarDeSeguirUsuario (@PathVariable Integer meuId,
            @PathVariable Integer idSeguindo)
            throws SQLException, PessoaNaoCadastradaException;


}
