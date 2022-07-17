package br.com.javafy.controller;


import br.com.javafy.documentation.DocumentationSeguidores;
import br.com.javafy.exceptions.BancoDeDadosException;
import br.com.javafy.service.SeguidoresService;
import br.com.javafy.dto.UsuarioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping(value = "/seguidores")
public class SeguidoresController implements DocumentationSeguidores {

    @Autowired
    SeguidoresService service;

    @GetMapping(value = "/from-user/{idUser}")// meus seuindo quem eu sigo
    public List<UsuarioDTO> fromUser(@PathVariable("idUser") Integer idUser) throws SQLException {
        return  service.getAllSeguidores(idUser);
    }

    @GetMapping(value = "/to-user/{idUser}") // quem me segue
    public List<UsuarioDTO> toUser(@PathVariable("idUser") Integer idUser) throws SQLException {
        return service.getAllSeguindo(idUser);
    }

    @PostMapping(value = "/{meuId}/seguir/{idSeguindo}")
    public Boolean seguirUser(@PathVariable("meuId") Integer meuId,
            @PathVariable("idSeguindo") Integer idSeguindo) throws BancoDeDadosException {
         return service.seguirUser(meuId,idSeguindo);
    }

    @DeleteMapping(value = "{idSeguindo}")
    public void deixarDeSeguirUsuario( @PathVariable("idSeguindo") Integer idSeguindo){

        service.deixarDeSeguirUsuario(idSeguindo);

    }
}
