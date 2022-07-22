package br.com.javafy.service;

import br.com.javafy.dto.ComentarioCreateDTO;
import br.com.javafy.dto.ComentarioDTO;
import br.com.javafy.entity.ComentarioEntity;
import br.com.javafy.exceptions.ComentarioNaoCadastradoException;
import br.com.javafy.exceptions.PessoaNaoCadastradaException;
import br.com.javafy.repository.ComentariosRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ComentarioService {

    @Autowired
    private ComentariosRepository comentariosRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PlayListService playListService;

    @Autowired
    private ObjectMapper objectMapper;

    public ComentarioDTO converterComentario(ComentarioEntity comentarioEntity) {
        return objectMapper.convertValue(comentarioEntity, ComentarioDTO.class);
    }

    public ComentarioEntity converterComentarioDTO(ComentarioDTO comentarioDTO) {
        return objectMapper.convertValue(comentarioDTO, ComentarioEntity.class);
    }

    public List<ComentarioDTO> list() {
        return comentariosRepository
                .findAll().stream()
                .map(this::converterComentario)
                .collect(Collectors.toList());
    }

    public ComentarioDTO create(Integer idUser, Integer idPlaylist, ComentarioCreateDTO comentarioCreateDTO) throws SQLException, PessoaNaoCadastradaException {
        ComentarioEntity comentarioEntity = converterComentarioDTO(new ComentarioDTO());
        comentarioEntity.setUsuarioEntity(usuarioService.retornaUsuarioEntityById(idUser));
        comentarioEntity.setPlayList(playListService.retornaPlaylistEntityById(idPlaylist));
        comentarioEntity.setComentario(comentarioCreateDTO.getComentario());
        comentariosRepository.save(comentarioEntity);
        return converterComentario(comentarioEntity);
    }

    public ComentarioDTO update(Integer idComentario,
                                ComentarioCreateDTO comentarioAtualizar)
            throws ComentarioNaoCadastradoException {
        ComentarioEntity comentarioEntity = converterComentarioDTO(findComentarioDTOById(idComentario));
        comentarioEntity.setComentario(comentarioAtualizar.getComentario());
        comentarioEntity.setPlayList(comentarioAtualizar.getPlayList());
        comentarioEntity.setUsuarioEntity(comentarioAtualizar.getUsuarioEntity());
        comentariosRepository.save(comentarioEntity);
        return converterComentario(comentarioEntity);
    }

    public void delete(Integer idComentario) {
        comentariosRepository.deleteById(idComentario);
    }

    public ComentarioEntity findComentarioEntityById(Integer id) throws ComentarioNaoCadastradoException {
        return comentariosRepository.findById(id)
                .orElseThrow(() -> new ComentarioNaoCadastradoException("Comentário não encontrado"));
    }

    public ComentarioDTO findComentarioDTOById(Integer id) throws ComentarioNaoCadastradoException {
        return converterComentario(findComentarioEntityById(id));
    }
}
