package br.com.javafy.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class ComentarioDTO {

    @Schema(description = "identificador unico do comentario")
    private Integer idComentario;
    @Schema(description = "identificador unico da playlist")
    private Integer idPlaylist;
    @Schema(description = "identificador unico do usuario")
    private Integer idUser;
    @Schema(description = "comentario sobre a playlist")
    @Size(max = 255)
    private String comentario;
}
