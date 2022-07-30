package br.com.javafy.controller;


import br.com.javafy.documentation.DocumentationComentario;
import br.com.javafy.dto.comentario.ComentarioCreateDTO;
import br.com.javafy.dto.comentario.ComentarioDTO;
import br.com.javafy.exceptions.ComentarioNaoCadastradoException;
import br.com.javafy.exceptions.PessoaException;
import br.com.javafy.service.ComentarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@RequestMapping("/comentario")
public class ComentarioController implements DocumentationComentario {

    @Autowired
    private ComentarioService comentarioService;

    @GetMapping("/{idComentario}")
    public ResponseEntity<ComentarioDTO> findById(@PathVariable("idComentario") Integer idComentario)
            throws ComentarioNaoCadastradoException {
        return ResponseEntity.ok(comentarioService.findComentarioDTOById(idComentario));
    }

    @GetMapping
    public ResponseEntity<List<ComentarioDTO>> list() {
        return ResponseEntity.ok(comentarioService.list());
    }

    @PostMapping("/add-in-playlist/{idPlaylist}")
    public ResponseEntity<ComentarioDTO> create(@PathVariable("idPlaylist")Integer idPlaylist,
                                                @Valid @RequestBody ComentarioCreateDTO comentarioCreateDTO)
            throws ComentarioNaoCadastradoException {
        return ResponseEntity.ok(comentarioService.create(idPlaylist, comentarioCreateDTO));
    }

    @PutMapping("/{idComentario}")
    public ResponseEntity<ComentarioDTO> update(@PathVariable("idComentario") Integer idComentario
            , @Valid @RequestBody ComentarioCreateDTO comentarioAtualizar)
            throws ComentarioNaoCadastradoException, PessoaException {
        return ResponseEntity.ok(comentarioService.update(idComentario, comentarioAtualizar));
    }

    @DeleteMapping("/{idComentario}")
    public void delete(@PathVariable("idComentario") Integer id)
            throws ComentarioNaoCadastradoException, PessoaException {
        comentarioService.delete(id);
    }
}
