
package br.com.javafy.controller;


import br.com.javafy.documentation.DocumentationSeguidores;
import br.com.javafy.exceptions.PessoaException;
import br.com.javafy.exceptions.SeguidoresException;
import br.com.javafy.service.SeguidoresService;
import br.com.javafy.dto.usuario.UsuarioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(value = "/seguidores")
public class SeguidoresController implements DocumentationSeguidores {

    @Autowired
    SeguidoresService service;

    @GetMapping(value = "/to-user")
    public List<UsuarioDTO> fromUser()
            throws PessoaException {
        return service.getAllSeguindo();
    }

    @GetMapping(value = "/from-user")
    public List<UsuarioDTO> toUser()
            throws PessoaException {
        return service.getAllSeguidores();
    }

    @PostMapping(value = "/seguir-usuario/{idSeguindo}")
    public boolean seguirUser(@PathVariable("idSeguindo") Integer idSeguindo)
            throws PessoaException, SeguidoresException {
        return service.seguirUser(idSeguindo);
    }

    @DeleteMapping(value = "/deixarDeSeguir-usuario/{idSeguindo}")
    public boolean deixarDeSeguirUsuario(@PathVariable("idSeguindo") Integer idSeguindo)
            throws PessoaException, SeguidoresException {
        return service.deixarDeSeguirUsuario(idSeguindo);
    }
}