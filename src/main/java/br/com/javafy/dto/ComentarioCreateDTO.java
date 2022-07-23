package br.com.javafy.dto;

import br.com.javafy.entity.PlayListEntity;
import br.com.javafy.entity.UsuarioEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class ComentarioCreateDTO {

    @Schema(hidden = false)
    private Integer idPlaylist;

    @Schema(hidden = false)
    private Integer idUser;

    @Schema(description = "Comentario sobre a playlist")
    @Size(max = 255)
    private String comentario;

    @Schema(hidden = true)
    private PlayListEntity playList;

    @Schema(hidden = true)
    private UsuarioEntity usuarioEntity;
}
