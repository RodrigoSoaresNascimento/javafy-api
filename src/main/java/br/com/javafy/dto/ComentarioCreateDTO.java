package br.com.javafy.dto;

import br.com.javafy.entity.PlayListEntity;
import br.com.javafy.entity.UsuarioEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class ComentarioCreateDTO {

    @Schema(hidden = true, description = "identificador unico da playlist")
    private Integer idPlaylist;

    @Schema(hidden = true, description = "identificador unico do usuario")
    private Integer idUser;

    @Schema(example = "Musica muito elétrica",description = "Comentario do usuario")
    @Size(max = 255)
    private String comentario;

    @Schema(hidden = true, description = "Playlist onde vai o comentario")
    private PlayListEntity playList;

    @Schema(hidden = true, description = "usuario dono da playlist")
    private UsuarioEntity usuarioEntity;
}
