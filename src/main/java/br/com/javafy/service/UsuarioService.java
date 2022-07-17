package br.com.javafy.service;

import br.com.javafy.entity.Usuario;
import br.com.javafy.enums.TipoDeMensagem;
import br.com.javafy.repository.UsuarioRepository;
import br.com.javafy.dto.UsuarioCreateDTO;
import br.com.javafy.dto.UsuarioDTO;
import br.com.javafy.exceptions.PessoaNaoCadastradaException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
@Slf4j
public class UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EmailService emailService;

    public Usuario converterUsuarioDTO(UsuarioCreateDTO usuarioCreateDTO) {
        return objectMapper.convertValue(usuarioCreateDTO, Usuario.class);
    }

    public UsuarioDTO converterUsuario(Usuario usuario) {
        return objectMapper.convertValue(usuario, UsuarioDTO.class);
    }

    public UsuarioDTO findById(Integer id) throws PessoaNaoCadastradaException, SQLException {
        return converterUsuario(usuarioRepository.findByID(id));
    }

    public List<UsuarioDTO> list() throws SQLException {
        return usuarioRepository
                .list().stream()
                .map(this::converterUsuario)
                .toList();
    }

    public UsuarioDTO create(UsuarioCreateDTO usuario) throws SQLException {
        Usuario usuarioEntity = converterUsuarioDTO(usuario);
        usuarioRepository.create(usuarioEntity);
        String tipodeMensagem = TipoDeMensagem.CREATE.getTipoDeMensagem();
        //emailService.sendEmail(converterUsuario(usuarioEntity), tipodeMensagem);
        return converterUsuario(usuarioEntity);
    }

    public UsuarioDTO update(UsuarioCreateDTO usuarioDTOAtualizar, Integer idUsuario) throws PessoaNaoCadastradaException, SQLException {

        Usuario usuario = converterUsuarioDTO(usuarioDTOAtualizar);
        UsuarioDTO atualizado = converterUsuario(usuario);

        usuarioRepository.update(idUsuario, usuario);

        String tipodeMensagem = TipoDeMensagem.UPDATE.getTipoDeMensagem();
        //emailService.sendEmail(converterUsuario(usuario), tipodeMensagem);

        return atualizado;
    }

    public void delete(Integer idUsuario) throws PessoaNaoCadastradaException, SQLException {
        UsuarioDTO usuarioRecuperado = findById(idUsuario);
        Usuario usuarioEntity = converterUsuarioDTO(usuarioRecuperado);

        String tipodeMensagem = TipoDeMensagem.DELETE.getTipoDeMensagem();
        //emailService.sendEmail(converterUsuario(usuarioEntity), tipodeMensagem);

        usuarioRepository.delete(idUsuario);
    }


}
