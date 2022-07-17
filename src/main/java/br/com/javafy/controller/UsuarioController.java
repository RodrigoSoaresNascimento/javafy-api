package br.com.javafy.controller;

import br.com.javafy.service.UsuarioService;
import br.com.javafy.dto.UsuarioCreateDTO;
import br.com.javafy.dto.UsuarioDTO;
import br.com.javafy.exceptions.PessoaNaoCadastradaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.SQLException;
import java.util.List;

@Validated
@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;

    @GetMapping("/{idUser}")
    public ResponseEntity<UsuarioDTO> findById(@PathVariable("idUser") Integer idUser)
            throws SQLException, PessoaNaoCadastradaException {
        return ResponseEntity.ok(usuarioService.findById(idUser));
    }

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> list() throws SQLException {
        return ResponseEntity.ok(usuarioService.list());
    }

    @PostMapping
    public ResponseEntity<UsuarioDTO> create(@Valid @RequestBody UsuarioCreateDTO usuario)
            throws SQLException {
        return ResponseEntity.ok(usuarioService.create(usuario));
    }

    @PutMapping("/{idUser}")
    public ResponseEntity<UsuarioDTO> update(@PathVariable("idUser") Integer id
            , @Valid @RequestBody UsuarioCreateDTO usuario) throws PessoaNaoCadastradaException, SQLException {
        return ResponseEntity.ok(usuarioService.update(usuario, id));
    }

    @DeleteMapping("/{idUser}")
    public void delete(@PathVariable("idUser") Integer id) throws PessoaNaoCadastradaException, SQLException {
        usuarioService.delete(id);
    }
}