
package br.com.javafy.controller;


import br.com.javafy.documentation.DocumentationSeguidores;
import br.com.javafy.exceptions.PessoaNaoCadastradaException;
import br.com.javafy.exceptions.SeguidoresException;
import br.com.javafy.service.SeguidoresService;
import br.com.javafy.dto.UsuarioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(value = "/seguidores")
public class SeguidoresController implements DocumentationSeguidores {

    @Autowired
    SeguidoresService service;

    @GetMapping(value = "/to-user/{idUser}")
    public List<UsuarioDTO> fromUser(@PathVariable("idUser") Integer idUser)
            throws PessoaNaoCadastradaException {
        return service.getAllSeguindo(idUser);
    }

    @GetMapping(value = "/from-user/{idUser}")
    public List<UsuarioDTO> toUser(@PathVariable("idUser") Integer idUser)
            throws PessoaNaoCadastradaException {
        return service.getAllSeguidores(idUser);
    }

    @PostMapping(value = "/{meuId}/seguir/{idSeguindo}")
    public Boolean seguirUser(@PathVariable("meuId") Integer meuId,
                              @PathVariable("idSeguindo") Integer idSeguindo)
            throws PessoaNaoCadastradaException, SeguidoresException {
        return service.seguirUser(meuId,idSeguindo);
    }

    @DeleteMapping(value = "{meuId}/deixarSeguir/{idSeguindo}")
    public void deixarDeSeguirUsuario( @PathVariable("meuId") Integer meuId,
            @PathVariable("idSeguindo") Integer idSeguindo)
            throws PessoaNaoCadastradaException {
        service.deixarDeSeguirUsuario(meuId,idSeguindo);
    }
}