package br.com.javafy.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.Size;

@Data
public class ComentarioDTO extends ComentarioCreateDTO {

    @Schema(description = "Identificador unico do comentario")
    private Integer idComentario;

}
