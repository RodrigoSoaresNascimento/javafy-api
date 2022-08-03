package br.com.javafy.service;


import br.com.javafy.dto.usuario.UsuarioDTO;
import br.com.javafy.entity.UsuarioEntity;
import br.com.javafy.exceptions.PessoaException;
import br.com.javafy.repository.UsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SeguidoresService {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ObjectMapper objectMapper;

    public UsuarioDTO converterParaUsuarioDTO(UsuarioEntity user) {
        return objectMapper.convertValue(user, UsuarioDTO.class);
    }

    public List<UsuarioDTO> getAllSeguidores()
            throws PessoaException {
        return usuarioService.retornarUsuarioEntityById()
                .getSeguidores()
                .stream()
                .map(this::converterParaUsuarioDTO)
                .collect(Collectors.toList());
    }

    public List<UsuarioDTO> getAllSeguindo()
            throws PessoaException {
        return usuarioService.retornarUsuarioEntityById()
                .getSeguindo()
                .stream()
                .map(this::converterParaUsuarioDTO)
                .collect(Collectors.toList());
    }

    public boolean seguirUser(Integer idSeguindo)
            throws PessoaException {
        UsuarioEntity usuario = usuarioService.retornarUsuarioEntityById();
        UsuarioEntity usuarioParaSeguir = usuarioService.buscarOutroUsuario(idSeguindo);
        usuario.getSeguidores().add(usuarioParaSeguir);
        repository.save(usuario);
        return true;
    }

    public boolean deixarDeSeguirUsuario(Integer idSeguindo)
            throws PessoaException {

        UsuarioEntity usuario = usuarioService.retornarUsuarioEntityById();
        UsuarioEntity usuarioParaSeguir = usuarioService.buscarOutroUsuario(idSeguindo);

        usuario.getSeguidores().remove(usuarioParaSeguir);

        repository.save(usuario);
        return true;
    }

}
