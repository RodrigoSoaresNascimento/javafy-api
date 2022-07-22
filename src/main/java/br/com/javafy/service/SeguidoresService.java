package br.com.javafy.service;


import br.com.javafy.entity.Usuario;
import br.com.javafy.exceptions.BancoDeDadosException;
import br.com.javafy.repository.SeguidoresRepository;
import br.com.javafy.dto.UsuarioDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SeguidoresService {

    @Autowired
    SeguidoresRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    public UsuarioDTO converterParaUsuarioDTO (Usuario user){
        return objectMapper.convertValue(user, UsuarioDTO.class);
    }

    public Usuario converterDTOParaUsuario (UsuarioDTO user){
        return objectMapper.convertValue(user, Usuario.class);
    }

    public List<UsuarioDTO> getAllSeguidores(Integer idUser) throws SQLException {

//        return repository.getAllSeguidores(idUser).stream()
//                .map(this::converterParaUsuarioDTO)
//                .collect(Collectors.toList());
        return null;
    }

    public List<UsuarioDTO> getAllSeguindo(Integer idUser) throws SQLException {

//        return repository.getAllSeguindo(idUser)
//                .stream()
//                .map(this::converterParaUsuarioDTO)
//                .collect(Collectors.toList());
        return null;
    }

    public boolean seguirUser(Integer meuId,Integer idSeguindo) throws BancoDeDadosException {
//        boolean seguir = false;
//        try {
//             seguir = repository.seguirUsuario(meuId,idSeguindo);
//        } catch (SQLException e) {
//            e.getStackTrace();
//        }
//        return seguir;
        return false;
    }

    public void deixarDeSeguirUsuario(Integer meuId, Integer idSeguindo) {
//        boolean deixouDeSeguir = false;
//        try {
//            deixouDeSeguir = repository.deixarDeSeguirUsuario(meuId, idSeguindo);
//        } catch (SQLException e) {
//           e.getStackTrace();
//        }
        return;

    }
}
