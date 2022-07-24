package br.com.javafy.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ComentarioPlaylistRelatorioDTO {

    @Schema(example = "Muito bom")
    private String comentario;
    @Schema(example = "Minha musicas")
    private String nomePlaylist;
    @Schema(example = "Duda")
    private String nomeUsuario;
    @Schema(example = "Duda@gmail.com")
    private String email;

}
