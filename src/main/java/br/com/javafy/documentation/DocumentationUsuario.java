package br.com.javafy.documentation;

import br.com.javafy.anotations.MagiaResponse;
import br.com.javafy.dto.PageDTO;
import br.com.javafy.dto.usuario.UsuarioDTO;
import br.com.javafy.dto.usuario.UsuarioUpdateDTO;
import br.com.javafy.enums.CargosUser;
import br.com.javafy.exceptions.PessoaException;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;
@Validated
public interface DocumentationUsuario {

    @Operation(summary = "Procura um usuario pelo seu ID")
    @MagiaResponse
    public ResponseEntity<UsuarioDTO> findById()
            throws PessoaException;

    @Operation(summary = "Lista todos os usuarios")
    @MagiaResponse
    public ResponseEntity<List<UsuarioDTO>> list();

    @Operation(summary = "Atualiza um perfil de usuario pelo seu ID")
    @MagiaResponse
    public ResponseEntity<UsuarioDTO> update(
            CargosUser cargos,
            @Valid @RequestBody UsuarioUpdateDTO usuario)
            throws PessoaException, JsonProcessingException;

    @Operation(summary = "Apaga um perfil de usuario pelo seu ID")
    @MagiaResponse
    public void delete()
            throws PessoaException;

    @Operation(summary = "Tras a paginação dos usuarios que estão cadastrados no aplicativo")
    @MagiaResponse
    PageDTO<UsuarioDTO> listarUsuariosPaginados(Integer pagina, Integer registro);

    }
