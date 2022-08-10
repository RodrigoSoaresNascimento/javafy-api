package br.com.javafy.documentation;

import br.com.javafy.anotations.MagiaResponse;
import br.com.javafy.dto.posts.PostsCreateDTO;
import br.com.javafy.dto.posts.PostsDTO;
import br.com.javafy.exceptions.PessoaException;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface DocumentationPosts {

    @Operation(summary = "Exibe todos os posts",
            description = "Exibe uma lista de postagem dos usuarios do aplicativo")
    @MagiaResponse
    ResponseEntity<List<PostsDTO>> getAll();

    @Operation(summary = "Cria um post",
            description = "Cria uma nova postagem de um tema qualquer relacionado a música")
    @MagiaResponse
    ResponseEntity<PostsDTO> create (PostsCreateDTO postsCreateDTO) throws PessoaException;
}
