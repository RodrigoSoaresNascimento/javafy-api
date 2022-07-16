package br.com.javafy.controller;


import br.com.javafy.dto.PlayListDTO;
import br.com.javafy.exceptions.PessoaNaoCadastradaException;
import br.com.javafy.service.PlayListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.SQLException;

@RestController
@RequestMapping("/playlist")
@Validated
public class PlaylistController {

    @Autowired
    PlayListService service;

    @PostMapping(value = "/{idUser}")
    public PlayListDTO create (@Valid @RequestBody PlayListDTO playListDTO,
                               @PathVariable("idUser") Integer id) throws SQLException {
        return service.create(playListDTO);
    }

    @PutMapping(value = "/{idPlayList}")
    public PlayListDTO update (@Valid @RequestBody PlayListDTO playListDTO,
                               @PathVariable("idPlaylist") Integer id ) throws SQLException, PessoaNaoCadastradaException {
        return service.update(playListDTO, id);
    }

    @DeleteMapping(value = "/{idPlaylist}")
    public void delete (@PathVariable("idPlaylist") Integer id) throws SQLException, PessoaNaoCadastradaException {
        service.delete(id);
    }

    @GetMapping("/byname")
    public PlayListDTO listByName (@Valid @RequestParam(value = "name") String name) throws SQLException, PessoaNaoCadastradaException {
        return service.listByName(name);
    }

}
