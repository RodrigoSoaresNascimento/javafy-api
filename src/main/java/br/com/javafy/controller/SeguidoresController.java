package br.com.javafy.controller;


import br.com.javafy.dto.UserDTO;
import br.com.javafy.exceptions.BancoDeDadosException;
import br.com.javafy.service.SeguidoresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/seguidores")
public class SeguidoresController {

    @Autowired
    SeguidoresService service;

    @GetMapping(value = "/from-user/{idUser}")// meus seuindo quem eu sigo
    public void fromUser(@PathVariable("idUser") Integer idUser){
        service.getAllSeguidores(idUser);
    }

    @GetMapping(value = "/to-user/{idUser}") // quem me segue
    public void toUser(@PathVariable("idUser") Integer idUser){
        service.getAllSeguindo(idUser);
    }

    @PostMapping(value = "{idSeguindo}")
    public void seguirUser(@RequestBody UserDTO usuarioDTO,
                           @PathVariable("idSeguindo") Integer idSeguindo) throws BancoDeDadosException {
        service.seguirUser(usuarioDTO,idSeguindo);
    }

    @DeleteMapping(value = "{idSeguindo}")
    public void deixarDeSeguirUsuario(@RequestBody UserDTO usuarioDTO,
                                      @PathVariable("idSeguindo") Integer idSeguindo){

        service.deixarDeSeguirUsuario(usuarioDTO,idSeguindo);
    }
}
