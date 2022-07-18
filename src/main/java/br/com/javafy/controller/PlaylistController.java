package br.com.javafy.controller;


import br.com.javafy.documentation.DocumentationPlaylist;
import br.com.javafy.dto.playlist.PlayListCreate;
import br.com.javafy.dto.playlist.PlayListDTO;
import br.com.javafy.exceptions.PessoaNaoCadastradaException;
import br.com.javafy.exceptions.PlayListException;
import br.com.javafy.service.PlayListService;
import org.springframework.beans.factory.annotation.Autowired;
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

    public PlayListDTO getPlayList(Integer idPlaylist) throws SQLException, PessoaNaoCadastradaException {
        return null;
    }

    @Override
    @PostMapping("/{idUsuario}")
    public PlayListDTO create(@Valid  PlayListCreate playListCreate, Integer idUsuario) throws SQLException,
            PessoaNaoCadastradaException, PlayListException {
        return playListService.create(playListCreate, idUsuario);
    }

    public PlayListDTO update(PlayListDTO playListDTO, Integer id) throws SQLException, PessoaNaoCadastradaException {
        return null;
    }

    public void delete(Integer idPlayList) throws SQLException, PessoaNaoCadastradaException {

    }
}
