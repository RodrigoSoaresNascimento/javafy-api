package br.com.javafy.controller;

import br.com.javafy.documentation.DocumentationComents;
import br.com.javafy.dto.coments.ComentsCreateDTO;
import br.com.javafy.dto.coments.ComentsDTO;
import br.com.javafy.exceptions.PessoaException;
import br.com.javafy.service.ComentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/coments")
@RestController
@RequiredArgsConstructor
public class ComentsController implements DocumentationComents {

    private final ComentsService comentsService;

    @PostMapping
    public ResponseEntity<ComentsDTO> create(ComentsCreateDTO comentsCreateDTO) throws PessoaException {
        return ResponseEntity.ok(comentsService.create(comentsCreateDTO));
    }

    @GetMapping
    public ResponseEntity<List<ComentsDTO>> getAll() {
        return ResponseEntity.ok(comentsService.list());
    }
}
