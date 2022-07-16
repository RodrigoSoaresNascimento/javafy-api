package br.com.javafy.service;

import br.com.javafy.entity.Usuario;
import br.com.javafy.exceptions.BancoDeDadosException;
import br.com.javafy.repository.UsuarioRepository;
import br.com.javafy.service.dto.UsuarioCreateDTO;
import br.com.javafy.service.dto.UsuarioDTO;
import br.com.javafy.exceptions.PessoaNaoCadastradaException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    private ObjectMapper objectMapper;

    public Usuario converterUsuarioDTO (UsuarioCreateDTO usuarioCreateDTO){
        return objectMapper.convertValue(usuarioCreateDTO, Usuario.class);
    }

    public UsuarioDTO converterUsuario (Usuario usuario){
        return objectMapper.convertValue(usuario, UsuarioDTO.class);
    }

    public UsuarioDTO create (UsuarioCreateDTO usuario){
        Usuario usuarioEntity = converterUsuarioDTO(usuario);
//        usuarioRepository.create(usuario);
        return converterUsuario(usuarioEntity);
    }

    public List<UsuarioDTO> list () {
        return null;
//                usuarioRepository.list()
//                .stream()
//                .map(this::converterOuvinte)
//                .collect(Collectors.toList());
    }

    public UsuarioDTO update (UsuarioCreateDTO usuarioAtualizar, Integer id) throws PessoaNaoCadastradaException, SQLException {
        Usuario usuarioRecuperado = findById(id);
        usuarioRecuperado.setNome(usuarioAtualizar.getNome());
        usuarioRecuperado.setEmail(usuarioAtualizar.getEmail());
        usuarioRecuperado.setGenero(usuarioAtualizar.getGenero());
        usuarioRecuperado.setDataNascimento(usuarioAtualizar.getDataNascimento());
        usuarioRecuperado.setPlano(usuarioAtualizar.getPlano());
        return converterUsuario(usuarioRecuperado);
    }

    public Usuario findById (Integer id) throws PessoaNaoCadastradaException, SQLException {
        Usuario usuarioRecuperado = usuarioRepository.listar().stream()
                .filter(usuario -> usuario.getIdUsuario().equals(id))
                .findFirst()
                .orElseThrow(() -> new PessoaNaoCadastradaException("Pessoa não econtrada"));
        return usuarioRecuperado;
    }

    public void delete (Integer id) throws PessoaNaoCadastradaException, SQLException {
        Usuario usuarioRecuperado = findById(id);
        usuarioRepository.listar().remove(usuarioRecuperado);
    }

    public List<UsuarioDTO> listByName (String nome) throws SQLException {
        return usuarioRepository.listar().stream()
                .filter(usuario -> usuario.getNome().toUpperCase().contains(nome.toUpperCase()))
                .map(this::converterUsuario)
                .collect(Collectors.toList());
    }

}
