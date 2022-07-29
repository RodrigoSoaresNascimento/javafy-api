package br.com.javafy.documentation;

import br.com.javafy.anotations.MagiaResponse;
import br.com.javafy.dto.PageDTO;
import br.com.javafy.dto.usuario.UsuarioCreateDTO;
import br.com.javafy.dto.usuario.UsuarioDTO;
import br.com.javafy.exceptions.PessoaNaoCadastradaException;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;
@Validated
public interface DocumentationUsuario {

    @Operation(summary = "Procura um usuario pelo seu ID")
    @MagiaResponse
    public ResponseEntity<UsuarioDTO> findById(@PathVariable("idUser") Integer idUser)
            throws PessoaNaoCadastradaException;

    @Operation(summary = "Lista todos os usuarios")
    @MagiaResponse
    public ResponseEntity<List<UsuarioDTO>> list();

    @Operation(summary = "Cria um perfil de usuario")
    @MagiaResponse
    public ResponseEntity<UsuarioDTO> create(@Valid @RequestBody UsuarioCreateDTO usuario);

    @Operation(summary = "Atualiza um perfil de usuario pelo seu ID")
    @MagiaResponse
    public ResponseEntity<UsuarioDTO> update(@PathVariable("idUser") Integer id,
            @Valid @RequestBody UsuarioCreateDTO usuario)
            throws PessoaNaoCadastradaException;

    @Operation(summary = "Apaga um perfil de usuario pelo seu ID")
    @MagiaResponse
    public void delete(@PathVariable("idUser") Integer id)
            throws PessoaNaoCadastradaException;

    @Operation(summary = "Tras a paginação dos usuarios que estão cadastrados no aplicativo")
    @MagiaResponse
    PageDTO<UsuarioDTO> listarUsuariosPaginados(Integer pagina, Integer registro);

    }
