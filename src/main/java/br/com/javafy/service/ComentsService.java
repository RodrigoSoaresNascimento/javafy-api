package br.com.javafy.service;

import br.com.javafy.dto.coments.ComentsCreateDTO;
import br.com.javafy.dto.coments.ComentsDTO;
import br.com.javafy.entity.ComentsEntity;
import br.com.javafy.entity.UsuarioEntity;
import br.com.javafy.exceptions.PessoaException;
import br.com.javafy.repository.ComentsRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ComentsService {

    private final ComentsRepository comentsRepository;

    private final UsuarioService usuarioService;

    private final ObjectMapper objectMapper;

    public ComentsDTO create(ComentsCreateDTO comentsCreateDTO) throws PessoaException {
        UsuarioEntity usuario = usuarioService.retornarUsuarioEntityById();
        ComentsEntity comentsEntity = objectMapper.convertValue(comentsCreateDTO, ComentsEntity.class);
        comentsEntity.setIdUsuario(usuario.getIdUsuario());
        comentsRepository.save(comentsEntity);
        return objectMapper.convertValue(comentsEntity, ComentsDTO.class);
    }

    public List<ComentsDTO> list() {
        return comentsRepository.findAll().stream()
                .map(comentsEntity -> objectMapper.convertValue(comentsEntity, ComentsDTO.class))
                .toList();
    }
}
