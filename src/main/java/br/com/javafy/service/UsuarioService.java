package br.com.javafy.service;

import br.com.javafy.dto.*;
import br.com.javafy.dto.usuario.UsuarioCreateDTO;
import br.com.javafy.dto.usuario.UsuarioDTO;
import br.com.javafy.dto.usuario.UsuarioRelatorioDTO;
import br.com.javafy.entity.UsuarioEntity;
import br.com.javafy.enums.TipoDeMensagem;
import br.com.javafy.exceptions.PessoaNaoCadastradaException;
import br.com.javafy.repository.UsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EmailService emailService;

    public UsuarioEntity converterUsuarioEntity(UsuarioCreateDTO usuarioCreateDTO) {
        return objectMapper.convertValue(usuarioCreateDTO, UsuarioEntity.class);
    }

    public UsuarioDTO converterUsuarioDTO(UsuarioEntity usuario) {
        return objectMapper.convertValue(usuario, UsuarioDTO.class);
    }

    private String encodePassword(String password){
        return new BCryptPasswordEncoder().encode(password);
    }

    public void validUsuario(Integer idUser) throws SQLException, PessoaNaoCadastradaException {
        UsuarioDTO usuarioDTO = findById(idUser);

        if(usuarioDTO.getIdUsuario() == null){
            throw new PessoaNaoCadastradaException("Usuário não cadastrado. ID " + idUser);
        }
    }

    public UsuarioEntity retornaUsuarioEntityById(Integer id) throws PessoaNaoCadastradaException {
        return usuarioRepository
                .findById(id)
                .orElseThrow(() -> new PessoaNaoCadastradaException("Usuário não cadastrado"));
    }

    public UsuarioDTO findById(Integer id) throws PessoaNaoCadastradaException {
        return converterUsuarioDTO(retornaUsuarioEntityById(id));
    }

    public PageDTO<UsuarioDTO> listarUsuariosPorNomePaginado(Integer pagina, Integer registro){
        PageRequest pageRequest = PageRequest.of(pagina, registro);
        Page<UsuarioEntity> page = usuarioRepository.findAll(pageRequest);
        List<UsuarioDTO> usuarioDTOS = page.getContent().stream()
                .map(usuarioEntity -> objectMapper.convertValue(usuarioEntity, UsuarioDTO.class))
                .toList();
        return new PageDTO<>(page.getTotalElements(), page.getTotalPages(), pagina, registro, usuarioDTOS);
    }

    public List<UsuarioDTO> list()  {
        return usuarioRepository
                .findAll()
                .stream()
                .map(this::converterUsuarioDTO)
                .toList();
    }

    public Optional<UsuarioEntity> findByLogin(String login){
        return usuarioRepository.findByLogin(login);
    }

    public UsuarioDTO create(UsuarioCreateDTO usuario)  {
        UsuarioEntity usuarioEntity = converterUsuarioEntity(usuario);
        usuarioEntity.setSenha(encodePassword(usuario.getSenha()));
        usuarioEntity = usuarioRepository.save(usuarioEntity);
        UsuarioDTO usuarioDTO= converterUsuarioDTO(usuarioEntity);
        //emailService.sendEmail(usuarioDTO, TipoDeMensagem.CREATE.getTipoDeMensagem());
        return usuarioDTO;
    }

    public UsuarioDTO update(UsuarioCreateDTO usuarioDTOAtualizar, Integer idUsuario)
            throws PessoaNaoCadastradaException {

        UsuarioEntity usuario = retornaUsuarioEntityById(idUsuario);
        usuario.setEmail(usuarioDTOAtualizar.getEmail());
        usuario.setNome(usuarioDTOAtualizar.getNome());
        usuario.setDataNascimento(usuarioDTOAtualizar.getDataNascimento());
        usuario.setGenero(usuarioDTOAtualizar.getGenero());
        usuario.setPlano(usuarioDTOAtualizar.getPlano());

        return converterUsuarioDTO(usuarioRepository.save(usuario));
    }

    public void delete(Integer idUsuario) throws PessoaNaoCadastradaException {

        UsuarioEntity usuario = retornaUsuarioEntityById(idUsuario);
        usuarioRepository.delete(usuario);
    }

    public List<UsuarioRelatorioDTO> relatorio (Integer idUsuario){
         return usuarioRepository.relatorioPessoa(idUsuario);
    }

}
