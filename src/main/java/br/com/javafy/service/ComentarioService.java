package br.com.javafy.service;

import br.com.javafy.dto.*;
import br.com.javafy.dto.comentario.ComentarioCreateDTO;
import br.com.javafy.dto.comentario.ComentarioDTO;
import br.com.javafy.dto.comentario.ComentarioPlaylistRelatorioDTO;
import br.com.javafy.entity.ComentarioEntity;
import br.com.javafy.entity.UsuarioEntity;
import br.com.javafy.enums.Roles;
import br.com.javafy.exceptions.ComentarioNaoCadastradoException;
import br.com.javafy.exceptions.PessoaException;
import br.com.javafy.repository.ComentariosRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

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

    public ComentarioEntity findComentarioEntityById(Integer id) throws ComentarioNaoCadastradoException {
        return comentariosRepository.findById(id)
                .orElseThrow(() -> new ComentarioNaoCadastradoException("Comentário não encontrado"));
    }

    public ComentarioDTO findComentarioDTOById(Integer id) throws ComentarioNaoCadastradoException {
        return converterComentario(findComentarioEntityById(id));
    }

    public List<ComentarioPlaylistRelatorioDTO> relatorioComentarioPlaylist () {
        return comentariosRepository.relatorioComentarios();
    }

    public List<ComentarioDTO> list() {
        return comentariosRepository
                .findAll().stream()
                .map(this::converterComentario)
                .collect(Collectors.toList());
    }

    public PageDTO<ComentarioDTO> listarComentariosPaginado(Integer pagina, Integer registro){
        PageRequest pageRequest = PageRequest.of(pagina, registro);
        Page<ComentarioEntity> page = comentariosRepository.findAll(pageRequest);
        List<ComentarioDTO> comentarioDTOS = page.getContent().stream()
                .map(comentarioEntity -> objectMapper.convertValue(comentarioEntity, ComentarioDTO.class))
                .toList();
        return new PageDTO<>(page.getTotalElements(), page.getTotalPages(), pagina, registro, comentarioDTOS);
    }

    public ComentarioDTO create(Integer idPlaylist, ComentarioCreateDTO comentarioCreateDTO)
            throws ComentarioNaoCadastradoException {
        try {
            ComentarioEntity comentarioEntity = converterComentarioDTO(new ComentarioDTO());
            comentarioEntity.setUsuarioEntity(usuarioService.retornarUsuarioEntityById());
            comentarioEntity.setPlayList(playListService.retornaPlaylistEntityById(idPlaylist));
            comentarioEntity.setComentario(comentarioCreateDTO.getComentario());
            comentariosRepository.save(comentarioEntity);
            return converterComentario(comentarioEntity);
        } catch (Exception e){
            throw new ComentarioNaoCadastradoException("Não foi possível cadastrar o comentário");
        }
    }

    public ComentarioDTO update(Integer idComentario,
                                ComentarioCreateDTO comentarioAtualizar)
            throws ComentarioNaoCadastradoException, PessoaException {

        ComentarioEntity comentarioEntity = findComentarioEntityById(idComentario);
        comentarioEhDoUsuarioOrAdminTaExcluindo(comentarioEntity);

        comentarioEntity.setComentario(comentarioAtualizar.getComentario());
        comentariosRepository.save(comentarioEntity);
        return converterComentario(comentarioEntity);
    }

    public void delete(Integer idComentario)
            throws ComentarioNaoCadastradoException, PessoaException {

        ComentarioEntity comentario = findComentarioEntityById(idComentario);
        comentarioEhDoUsuarioOrAdminTaExcluindo(comentario);
        comentariosRepository.delete(comentario);
    }

    private void comentarioEhDoUsuarioOrAdminTaExcluindo(ComentarioEntity comentario)
            throws PessoaException, ComentarioNaoCadastradoException {

        Integer idDonoDoComentario = comentario.getUsuarioEntity().getIdUsuario();
        UsuarioEntity usuario = usuarioService.retornarUsuarioEntityById();

        boolean userAdmin = usuario.getCargo().getNome().getTipoCargo()
                .equals(Roles.ADMIN);
        boolean usuarioEhDonoDoComentario = idDonoDoComentario
                .equals(usuario.getIdUsuario());



        if(userAdmin || usuarioEhDonoDoComentario){
            return;
        } else {
            throw new ComentarioNaoCadastradoException("Não é possível remover o comentário.");
        }
    }

}
