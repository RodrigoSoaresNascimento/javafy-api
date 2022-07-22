package br.com.javafy.service;


import br.com.javafy.entity.UsuarioEntity;
import br.com.javafy.exceptions.BancoDeDadosException;
import br.com.javafy.repository.SeguidoresRepository;
import br.com.javafy.dto.UsuarioDTO;
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

    public UsuarioDTO converterParaUsuarioDTO (UsuarioEntity user){
        return objectMapper.convertValue(user, UsuarioDTO.class);
    }

    public UsuarioEntity converterDTOParaUsuario (UsuarioDTO user){
        return objectMapper.convertValue(user, UsuarioEntity.class);
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
