package br.com.javafy.dto.usuario;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UsuarioRelatorioDTO {

    @Schema(example = "Duda")
    private String nome;
    @Schema(example = "duda@gmail.com")
    private String email;

    @Schema(example = "Rock")
    private String nomePlaylist;
}
