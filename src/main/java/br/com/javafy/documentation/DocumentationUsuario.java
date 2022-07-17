package br.com.javafy.documentation;

import br.com.javafy.anotations.MagiaResponse;
import br.com.javafy.dto.UsuarioCreateDTO;
import br.com.javafy.dto.UsuarioDTO;
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
public interface DocumentationUsuario {
    @Operation(summary = "Procura um usuario pelo seu ID")
    @MagiaResponse
    public ResponseEntity<UsuarioDTO> findById(@PathVariable("idUser") Integer idUser)
            throws SQLException, PessoaNaoCadastradaException;

    @Operation(summary = "Lista todos os usuarios pelo seu ID")
    @MagiaResponse
    public ResponseEntity<List<UsuarioDTO>> list() throws SQLException;

    @Operation(summary = "Cria um perfil de usuario")
    @MagiaResponse
    public ResponseEntity<UsuarioDTO> create(@Valid @RequestBody UsuarioCreateDTO usuario) throws SQLException;

    @Operation(summary = "Atualiza um perfil de usuario pelo seu ID")
    @MagiaResponse
    public ResponseEntity<UsuarioDTO> update(@PathVariable("idUser") Integer id
            , @Valid @RequestBody UsuarioCreateDTO usuario) throws PessoaNaoCadastradaException, SQLException;

    @Operation(summary = "Apaga um perfil de usuario pelo seu ID")
    @MagiaResponse
    public void delete(@PathVariable("idUser") Integer id) throws PessoaNaoCadastradaException, SQLException;

    }
