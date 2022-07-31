package br.com.javafy.documentation;

import br.com.javafy.anotations.MagiaResponse;
import br.com.javafy.dto.LoginDTO;
import br.com.javafy.dto.usuario.*;
import br.com.javafy.enums.CargosEnum;
import br.com.javafy.exceptions.PessoaException;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;

public interface DocumentationAuth {

    @Operation(summary = "Retorna um token de acesso",
            description = "Retorna um token de acesso ao usuario para acesso a aplicação")
    @MagiaResponse
    ResponseEntity<String> auth(LoginDTO loginDTO);

    @Operation(summary = "Cria um novo usuario",
            description = "Cria um usuario com login e senha")
    @MagiaResponse
    ResponseEntity<UsuarioDTO> create (UsuarioCreateDTO usuarioCreateDTO, CargosEnum cargosEnum);

    @Operation(summary = "Atualiza as credenciais",
            description = "Atualiza as credenciais de acesso a aplicação como senha e login")
    @MagiaResponse
    ResponseEntity<UsuarioUpdateLoginDTO> update (UsuarioUpdateLoginDTO usuarioUpdateLoginDTO) throws PessoaException;

    @Operation(summary = "Exibe um usuario logado",
            description = "Retorna o usuario logado na aplicação")
    @MagiaResponse
    ResponseEntity<UsuarioLoginDTO> getUser () throws PessoaException;

    @Operation(summary = "deleta o usuario",
            description = "Deleta uma conta de usuario atraves do cargo de admin")
    @MagiaResponse
    void delete (Integer login) throws PessoaException;
}
