package br.com.javafy.documentation;

import br.com.javafy.anotations.MagiaResponse;
import br.com.javafy.dto.coments.ComentsCreateDTO;
import br.com.javafy.dto.coments.ComentsDTO;
import br.com.javafy.exceptions.PessoaException;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface DocumentationComents {

    @Operation(summary = "Exibe todos os comentarios do usuario em postagens",
            description = "Exibe uma lista de comentarios realizados nas postagens de outros usuarios")
    @MagiaResponse
    ResponseEntity<List<ComentsDTO>> getAll();

    @Operation(summary = "Cria um coment",
            description = "Cria um novo comentario em uma postagem")
    @MagiaResponse
    ResponseEntity<ComentsDTO> create (ComentsCreateDTO comentsCreateDTO) throws PessoaException;

}
