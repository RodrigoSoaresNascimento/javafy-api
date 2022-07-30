package br.com.javafy.dto.comentario;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class ComentarioCreateDTO {

    @Schema(example = "Musica muito elétrica",description = "Comentario do usuario")
    @Size(max = 255)
    @NotNull
    private String comentario;

}
