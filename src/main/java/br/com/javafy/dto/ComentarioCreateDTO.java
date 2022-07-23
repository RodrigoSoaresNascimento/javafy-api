package br.com.javafy.dto;

import br.com.javafy.entity.PlayListEntity;
import br.com.javafy.entity.UsuarioEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class ComentarioCreateDTO {

    @Schema(hidden = true)
    private Integer idPlaylist;

    @Schema(hidden = true)
    private Integer idUser;

    @Schema(example = "Musica muito elétrica")
    @Size(max = 255)
    private String comentario;

    @Schema(hidden = true)
    private PlayListEntity playList;

    @Schema(hidden = true)
    private UsuarioEntity usuarioEntity;
}
