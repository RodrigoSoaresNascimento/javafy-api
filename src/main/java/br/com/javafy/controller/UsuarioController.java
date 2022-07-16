package br.com.javafy.controller;

import br.com.javafy.exceptions.BancoDeDadosException;
import br.com.javafy.service.UsuarioService;
import br.com.javafy.service.dto.UsuarioCreateDTO;
import br.com.javafy.service.dto.UsuarioDTO;
import br.com.javafy.exceptions.PessoaNaoCadastradaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.SQLException;
import java.util.List;

public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<UsuarioDTO> create(@Valid @RequestBody UsuarioCreateDTO usuario) {
        return ResponseEntity.ok(usuarioService.create(usuario));

    }

    @GetMapping
    public  ResponseEntity<List<UsuarioDTO>> list () {
        return ResponseEntity.ok(usuarioService.list());
    }

    @PutMapping("/{idUser}")
    public ResponseEntity<UsuarioDTO> update (@PathVariable("idUser") Integer id
                                                , @Valid @RequestBody UsuarioCreateDTO usuario) throws PessoaNaoCadastradaException, SQLException {
        return ResponseEntity.ok(usuarioService.update(usuario,id));
    }

    @DeleteMapping("/{idUser}")
    public void delete (@PathVariable ("idUser") Integer id) throws PessoaNaoCadastradaException, SQLException {
        usuarioService.delete(id);
    }

    @GetMapping("/byname")
    public  ResponseEntity<List<UsuarioDTO>> listByName (@RequestParam("nome=") String nome) throws SQLException {
        return ResponseEntity.ok(usuarioService.listByName(nome));
    }
}