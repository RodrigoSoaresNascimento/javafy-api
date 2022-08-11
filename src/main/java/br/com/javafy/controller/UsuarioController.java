package br.com.javafy.controller;

import br.com.javafy.documentation.DocumentationUsuario;
import br.com.javafy.dto.PageDTO;
import br.com.javafy.dto.usuario.UsuarioRelatorioDTO;
import br.com.javafy.dto.usuario.UsuarioUpdateDTO;
import br.com.javafy.entity.UsuarioEntity;
import br.com.javafy.enums.CargosUser;
import br.com.javafy.service.UsuarioService;
import br.com.javafy.dto.usuario.UsuarioDTO;
import br.com.javafy.exceptions.PessoaException;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@RequestMapping("/usuario")
public class UsuarioController implements DocumentationUsuario {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/find-by-id")
    public ResponseEntity<UsuarioDTO> findById()
            throws PessoaException {
        return ResponseEntity.ok(usuarioService.findById());
    }

    @GetMapping("/list-usuario")
    public ResponseEntity<List<UsuarioDTO>> list()  {
        return ResponseEntity.ok(usuarioService.list());
    }

    @GetMapping("/usuario-paginado")
    public PageDTO<UsuarioDTO> listarUsuariosPaginados( Integer pagina, Integer registro){
        return usuarioService.listarUsuariosPorNomePaginado(pagina, registro);
    }

    @PutMapping("/update-user")
    public ResponseEntity<UsuarioDTO> update(
            @RequestParam(required = false) CargosUser cargos,
            @Valid @RequestBody UsuarioUpdateDTO usuario
    )
            throws PessoaException, JsonProcessingException {
        return ResponseEntity.ok(usuarioService.update(usuario, cargos));
    }

    @DeleteMapping("/delete-user")
    public void delete() throws PessoaException {
        usuarioService.delete();
    }

    @GetMapping("/list-birthday")
    public List<UsuarioDTO> getBirthDay(){
        return usuarioService.listBirthDay();
    }
}