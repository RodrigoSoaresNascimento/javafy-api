package br.com.javafy.service;


import br.com.javafy.entity.UsuarioEntity;
import br.com.javafy.exceptions.PessoaNaoCadastradaException;
import br.com.javafy.dto.UsuarioDTO;
import br.com.javafy.exceptions.SeguidoresException;
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

    public UsuarioDTO converterParaUsuarioDTO (UsuarioEntity user){
        return objectMapper.convertValue(user, UsuarioDTO.class);
    }

    public List<UsuarioDTO> getAllSeguidores(Integer idUser)
            throws PessoaNaoCadastradaException {
        return usuarioService.retornaUsuarioEntityById(idUser)
                .getSeguidores()
                .stream()
                .map(this::converterParaUsuarioDTO)
                .collect(Collectors.toList());
    }

    public List<UsuarioDTO> getAllSeguindo(Integer idUser)
            throws PessoaNaoCadastradaException {
       return usuarioService.retornaUsuarioEntityById(idUser)
               .getSeguindo()
                .stream()
                .map(this::converterParaUsuarioDTO)
                .collect(Collectors.toList());
    }

    public boolean seguirUser(Integer meuId,Integer idSeguindo)
            throws PessoaNaoCadastradaException, SeguidoresException {
        UsuarioEntity usuario = usuarioService.retornaUsuarioEntityById(meuId);
        UsuarioEntity usuarioParaSeguir = usuarioService.retornaUsuarioEntityById(idSeguindo);
        usuario.getSeguidores().add(usuarioParaSeguir);

        try {
            repository.save(usuario);
            return true;
        } catch (Exception e){
            throw new SeguidoresException("Error ao seguir usuario");
        }
    }

    public void deixarDeSeguirUsuario(Integer meuId, Integer idSeguindo)
            throws PessoaNaoCadastradaException {

        UsuarioEntity usuario = usuarioService.retornaUsuarioEntityById(meuId);
        UsuarioEntity usuarioParaSeguir = usuarioService.retornaUsuarioEntityById(idSeguindo);

        usuario.getSeguidores().remove(usuarioParaSeguir);
        usuario.setIdUsuario(meuId);
        repository.save(usuario);

    }

}
