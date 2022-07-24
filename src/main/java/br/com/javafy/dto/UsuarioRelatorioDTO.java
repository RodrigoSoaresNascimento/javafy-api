package br.com.javafy.dto;

import br.com.javafy.enums.TiposdePlano;
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
    @Schema(example = "PREMIUM, FREE")
    private TiposdePlano plano;
    @Schema(example = "Rock")
    private String nomePlaylist;
}
