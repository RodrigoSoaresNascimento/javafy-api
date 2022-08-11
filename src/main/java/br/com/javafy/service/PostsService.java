package br.com.javafy.service;

import br.com.javafy.dto.posts.PostsCreateDTO;
import br.com.javafy.dto.posts.PostsDTO;
import br.com.javafy.entity.PostsEntity;
import br.com.javafy.entity.UsuarioEntity;
import br.com.javafy.exceptions.PessoaException;
import br.com.javafy.repository.PostsRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostsService {

    private final PostsRepository postsRepository;

    private final ObjectMapper objectMapper;

    private final UsuarioService usuarioService;

    public PostsDTO create(PostsCreateDTO postsCreateDTO) throws PessoaException {
        UsuarioEntity usuario = usuarioService.retornarUsuarioEntityById();
        PostsEntity postsEntity = objectMapper.convertValue(postsCreateDTO, PostsEntity.class);
        postsEntity.setIdUsuario(usuario.getIdUsuario());
        postsEntity.setDate(LocalDate.now(ZoneId.systemDefault()));
        postsEntity.setComents(List.of());
        postsRepository.save(postsEntity);
        return objectMapper.convertValue(postsEntity, PostsDTO.class);
    }

    public List<PostsDTO> list() {
        return postsRepository.findAll().stream()
                .map(postsEntity -> objectMapper.convertValue(postsEntity, PostsDTO.class))
                .toList();
    }


}

