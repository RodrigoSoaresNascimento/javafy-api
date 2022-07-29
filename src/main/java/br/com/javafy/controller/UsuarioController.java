package br.com.javafy.controller;

import br.com.javafy.documentation.DocumentationUsuario;
import br.com.javafy.dto.PageDTO;
import br.com.javafy.service.UsuarioService;
import br.com.javafy.dto.usuario.UsuarioCreateDTO;
import br.com.javafy.dto.usuario.UsuarioDTO;
import br.com.javafy.exceptions.PessoaNaoCadastradaException;
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

    @GetMapping("/{idUser}")
    public ResponseEntity<UsuarioDTO> findById(@PathVariable("idUser") Integer idUser)
            throws PessoaNaoCadastradaException {
        return ResponseEntity.ok(usuarioService.findById(idUser));
    }

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> list()  {
        return ResponseEntity.ok(usuarioService.list());
    }

    @GetMapping("usuario-paginado")
    public PageDTO<UsuarioDTO> listarUsuariosPaginados( Integer pagina, Integer registro){
        return usuarioService.listarUsuariosPorNomePaginado(pagina, registro);
    }

    @PostMapping
    public ResponseEntity<UsuarioDTO> create(@Valid @RequestBody UsuarioCreateDTO usuario) {
        return ResponseEntity.ok(usuarioService.create(usuario));
    }

    @PutMapping("/{idUser}")
    public ResponseEntity<UsuarioDTO> update(@PathVariable("idUser") Integer id,
            @Valid @RequestBody UsuarioCreateDTO usuario)
            throws PessoaNaoCadastradaException {
        return ResponseEntity.ok(usuarioService.update(usuario, id));
    }

    @DeleteMapping("/{idUser}")
    public void delete(@PathVariable("idUser") Integer id) throws PessoaNaoCadastradaException {
        usuarioService.delete(id);
    }

}