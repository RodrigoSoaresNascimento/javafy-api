package br.com.javafy.dto.comentario;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
public class ComentarioDTO extends ComentarioCreateDTO {

    @Schema(description = "Identificador unico do comentario")
    private Integer idComentario;

}
