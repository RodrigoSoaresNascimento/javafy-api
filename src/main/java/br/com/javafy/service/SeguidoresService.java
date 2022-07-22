package br.com.javafy.service;


import br.com.javafy.entity.UsuarioEntity;
import br.com.javafy.exceptions.BancoDeDadosException;
import br.com.javafy.exceptions.PessoaNaoCadastradaException;
import br.com.javafy.dto.UsuarioDTO;
import br.com.javafy.repository.UsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SeguidoresService {

    @Autowired
    UsuarioRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    public UsuarioDTO converterParaUsuarioDTO (UsuarioEntity user){
        return objectMapper.convertValue(user, UsuarioDTO.class);
    }

    public UsuarioEntity converterOptionalParaUsuarioEntity (Optional<UsuarioEntity> user){
        return objectMapper.convertValue(user, UsuarioEntity.class);
    }

    public UsuarioEntity converterDTOParaUsuario (UsuarioDTO user){
        return objectMapper.convertValue(user, UsuarioEntity.class);
    }

    public List<UsuarioDTO> getAllSeguidores(Integer idUser) throws SQLException, PessoaNaoCadastradaException {

        Optional<UsuarioEntity> usuarioOptional = repository.findById(idUser);

        if(usuarioOptional.isEmpty()){
            throw new PessoaNaoCadastradaException("Usuario não encontrado");
        }
        UsuarioEntity usuario = usuarioOptional.get();


        return usuarioOptional.get()
                .getSeguidores()
                .stream()
                .map(this::converterParaUsuarioDTO)
                .collect(Collectors.toList());
    }

    public List<UsuarioDTO> getAllSeguindo(Integer idUser) throws SQLException, PessoaNaoCadastradaException {

        Optional<UsuarioEntity> usuarioOptional = repository.findById(idUser);

       if(usuarioOptional.isEmpty()){
           throw new PessoaNaoCadastradaException("Usuario não encontrado");
       }
       UsuarioEntity usuario = usuarioOptional.get();


       return usuarioOptional.get()
               .getSeguindo()
                .stream()
                .map(this::converterParaUsuarioDTO)
                .collect(Collectors.toList());

    }

    public boolean seguirUser(Integer meuId,Integer idSeguindo) throws BancoDeDadosException {

        Optional<UsuarioEntity> optionalUsuario = repository.findById(meuId);
        Optional<UsuarioEntity> usuarioParaSeguir = repository.findById(idSeguindo);
        UsuarioEntity usuarioEntity = converterOptionalParaUsuarioEntity(optionalUsuario);
        return usuarioEntity.getSeguidores().add(converterOptionalParaUsuarioEntity(usuarioParaSeguir));

    }

    public void deixarDeSeguirUsuario(Integer meuId, Integer idSeguindo) {
        Optional<UsuarioEntity> optionalUsuario = repository.findById(meuId);
        Optional<UsuarioEntity> usuarioParaSeguir = repository.findById(idSeguindo);
        UsuarioEntity usuarioEntity = converterOptionalParaUsuarioEntity(optionalUsuario);
        usuarioEntity.getSeguidores().remove(converterOptionalParaUsuarioEntity(usuarioParaSeguir));

    }
}
