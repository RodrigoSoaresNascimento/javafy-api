package br.com.javafy.service;


import br.com.javafy.dto.UserDTO;
import br.com.javafy.entity.Usuario;
import br.com.javafy.exceptions.BancoDeDadosException;
import br.com.javafy.repository.SeguidoresRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class SeguidoresService {

    @Autowired
    SeguidoresRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    public UserDTO converterParaUsuarioDTO (Usuario user){
        return objectMapper.convertValue(user, UserDTO.class);
    }

    public Usuario converterDTOParaUsuario (UserDTO user){
        return objectMapper.convertValue(user, Usuario.class);
    }

    public void getAllSeguidores(Integer idUser)  {
        try {
            List<Usuario> usuarios = repository.getAllSeguidores(idUser);
        } catch (SQLException e) {
            e.getStackTrace();
        }
    }

    public void getAllSeguindo(Integer idUser) {
        try {
            List<Usuario> usuarios = repository.getAllSeguindo(idUser);

        } catch (SQLException e) {
            e.getStackTrace();
        }
    }

//    public void getAllUsers(Usuario usuario) {
//        try {
//            List<Usuario> usuarios = usuarioRepositorio.getAllUsers(usuario);
//
//        } catch (BancoDeDadosException e) {
//            e.getStackTrace();
//        }
//    }

    public UserDTO seguirUser(UserDTO usuarioDTO, Integer idSeguindo) throws BancoDeDadosException {
        try {

            boolean seguir = repository.seguirUsuario(idSeguindo, converterDTOParaUsuario(usuarioDTO));

        } catch (SQLException e) {
            e.getStackTrace();
        }
        return usuarioDTO;
    }

    public void deixarDeSeguirUsuario(UserDTO usuarioDTO, Integer idSeguindo) {
        try {
            repository.deixarDeSeguirUsuario(idSeguindo, converterDTOParaUsuario(usuarioDTO));
        } catch (SQLException e) {
           e.getStackTrace();
        }
    }
}
