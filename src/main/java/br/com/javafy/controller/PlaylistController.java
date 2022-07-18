package br.com.javafy.controller;


import br.com.javafy.documentation.DocumentationPlaylist;
import br.com.javafy.dto.playlist.PlayListCreate;
import br.com.javafy.dto.playlist.PlayListDTO;
import br.com.javafy.exceptions.PessoaNaoCadastradaException;
import br.com.javafy.exceptions.PlayListException;
import br.com.javafy.service.PlayListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.SQLException;

@RestController
@RequestMapping("/playlist")
@Validated
public class PlaylistController implements DocumentationPlaylist{

    @Autowired
    private PlayListService playListService;

    @GetMapping("/{idPlaylist}")
    public ResponseEntity<PlayListDTO> getPlayList(Integer idPlaylist) throws SQLException,
            PessoaNaoCadastradaException, PlayListException {
        return ResponseEntity.ok(playListService.getPlaylistById(idPlaylist));
    }

    @Override
    @PostMapping("/{idUsuario}")
    public PlayListDTO create(@Valid  PlayListCreate playListCreate, Integer idUsuario)
            throws SQLException,
            PessoaNaoCadastradaException, PlayListException {
        return playListService.create(playListCreate, idUsuario);
    }

    public PlayListDTO update(PlayListDTO playListDTO, Integer id) throws SQLException,
            PessoaNaoCadastradaException {
        return null;
    }

    // TODO
    @DeleteMapping("/{idPlayList}")
    public void delete(Integer idPlayList) throws SQLException, PessoaNaoCadastradaException,
            PlayListException {
        playListService.delete(idPlayList);
    }
}
