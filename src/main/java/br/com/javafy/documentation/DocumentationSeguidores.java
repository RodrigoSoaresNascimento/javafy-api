package br.com.javafy.documentation;

import br.com.javafy.anotations.MagiaResponse;
import br.com.javafy.dto.UsuarioDTO;
import br.com.javafy.dto.playlist.PlayListDTO;
import br.com.javafy.exceptions.PessoaNaoCadastradaException;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;

import java.sql.SQLException;
import java.util.List;

@Validated
public interface DocumentationSeguidores {

    @Operation(summary = "Retorna a lista de seguidores do usuario.")
    @MagiaResponse
    public List<UsuarioDTO> fromUser (@PathVariable Integer idUser)
            throws SQLException;

    @Operation(summary = "Retorna a lista de usuarios de seguem sua conta")
    @MagiaResponse
    public List<UsuarioDTO> toUser (@PathVariable Integer idUser) throws SQLException, PessoaNaoCadastradaException;

    @Operation(summary = "Segue uma conta de usuario")
    @MagiaResponse
    public Boolean seguirUser (@PathVariable Integer meuId,
                                   @PathVariable Integer idSeguindo)
            throws SQLException;

    @Operation(summary = "Deixa de seguir um usuario")
    @MagiaResponse
    public void deixarDeSeguirUsuario (@PathVariable Integer meuId,
            @PathVariable Integer idSeguindo) throws SQLException;


}
