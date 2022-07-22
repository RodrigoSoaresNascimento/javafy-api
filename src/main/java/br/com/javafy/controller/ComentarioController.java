package br.com.javafy.controller;


import br.com.javafy.documentation.DocumentationComentario;
import br.com.javafy.dto.ComentarioCreateDTO;
import br.com.javafy.dto.ComentarioDTO;
import br.com.javafy.dto.PageDTO;
import br.com.javafy.exceptions.ComentarioNaoCadastradoException;
import br.com.javafy.exceptions.PessoaNaoCadastradaException;
import br.com.javafy.service.ComentarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
            throws ComentarioNaoCadastradoException {
        return ResponseEntity.ok(comentarioService.findComentarioDTOById(idUser));
    }

    @GetMapping
    public ResponseEntity<List<ComentarioDTO>> list() {
        return ResponseEntity.ok(comentarioService.list());
    }

    @GetMapping("/comentario-paginado")
    public PageDTO<ComentarioDTO> listarComentariosPaginado(Integer idComentario, Integer pagina, Integer registro){
        return comentarioService.listarComentariosPaginado(idComentario, pagina, registro);
    }


    //TODO -> FAZER A VALIDAÇÃO DOS DADOS, USUARIO E PLAYLIST
    @PostMapping("/{idUser}/and/{idPlaylist}")
    public ResponseEntity<ComentarioDTO> create(@PathVariable("idUser")Integer idUser, @PathVariable("idPlaylist")Integer idPlaylist,
                                                @Valid @RequestBody ComentarioCreateDTO comentarioCreateDTO) throws PessoaNaoCadastradaException, SQLException {
        return ResponseEntity.ok(comentarioService.create(idUser, idPlaylist, comentarioCreateDTO));
    }

    @PutMapping("/{idComentario}")
    public ResponseEntity<ComentarioDTO> update(@PathVariable("idComentario") Integer idComentario
            , @Valid @RequestBody ComentarioCreateDTO comentarioAtualizar) throws ComentarioNaoCadastradoException {
        return ResponseEntity.ok(comentarioService.update(idComentario, comentarioAtualizar));
    }

    @DeleteMapping("/{idComentario}")
    public void delete(@PathVariable("idComentario") Integer id) {
        comentarioService.delete(id);
    }
}
