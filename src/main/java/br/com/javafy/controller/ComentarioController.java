package br.com.javafy.controller;


import br.com.javafy.documentation.DocumentationComentario;
import br.com.javafy.dto.ComentarioDTO;
import br.com.javafy.exceptions.PessoaNaoCadastradaException;
import br.com.javafy.service.ComentarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.SQLException;
import java.util.List;

@Validated
@RestController
@RequestMapping("/comentario")
public class ComentarioController implements DocumentationComentario {
    @Autowired
    ComentarioService comentarioService;

    @GetMapping("/{idUser}")
    public ResponseEntity<ComentarioDTO> findById(@PathVariable("idUser") Integer idUser)
            throws SQLException, PessoaNaoCadastradaException {
        return ResponseEntity.ok(comentarioService.findById(idUser));
    }

    @GetMapping
    public ResponseEntity<List<ComentarioDTO>> list() throws SQLException {
        return ResponseEntity.ok(comentarioService.list());
    }

    @PostMapping("/{idUser}/and/{idPlaylist}")
    public ResponseEntity<ComentarioDTO> create(@PathVariable("idUser")Integer idUser, @PathVariable("idPlaylist")Integer idPlaylist,
                                                @Valid @RequestBody ComentarioDTO comentario)throws SQLException {
        return ResponseEntity.ok(comentarioService.create(idUser, idPlaylist, comentario));
    }

    @PutMapping("/{idComentario}")
    public ResponseEntity<ComentarioDTO> update(@PathVariable("idComentario") Integer id
            , @Valid @RequestBody ComentarioDTO comentario) throws PessoaNaoCadastradaException,
            SQLException {
        return ResponseEntity.ok(comentarioService.update(comentario, id));
    }

    @DeleteMapping("/{idComentario}")
    public void delete(@PathVariable("idComentario") Integer id) throws PessoaNaoCadastradaException, SQLException {
        comentarioService.delete(id);
    }
}
