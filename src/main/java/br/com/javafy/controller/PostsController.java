package br.com.javafy.controller;

import br.com.javafy.dto.posts.PostsCreateDTO;
import br.com.javafy.dto.posts.PostsDTO;
import br.com.javafy.exceptions.PessoaException;
import br.com.javafy.service.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/posts")
public class PostsController {

    private final PostsService postsService;

    @GetMapping
    public List<PostsDTO> getAll() {
        return postsService.list();
    }

    @PostMapping()
    public PostsDTO post(@RequestBody PostsCreateDTO postsCreateDTO) throws PessoaException {
        return postsService.create(postsCreateDTO);
    }
}
