package br.com.javafy.controller;

import br.com.javafy.documentation.DocumentationPosts;
import br.com.javafy.dto.posts.PostsCreateDTO;
import br.com.javafy.dto.posts.PostsDTO;
import br.com.javafy.exceptions.PessoaException;
import br.com.javafy.service.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/posts")
public class PostsController implements DocumentationPosts {

    private final PostsService postsService;

    @GetMapping
    public ResponseEntity<List<PostsDTO>> getAll() {
        return  ResponseEntity.ok(postsService.list());
    }

    @PostMapping()
    public  ResponseEntity<PostsDTO> create (@RequestBody PostsCreateDTO postsCreateDTO) throws PessoaException {
        return ResponseEntity.ok(postsService.create(postsCreateDTO));
    }
}
