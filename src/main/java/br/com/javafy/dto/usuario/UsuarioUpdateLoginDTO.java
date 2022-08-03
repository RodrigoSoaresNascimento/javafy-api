package br.com.javafy.dto.usuario;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UsuarioUpdateLoginDTO {

    @Schema(description = "login do usuario", example = "joão")
    private String login;

    @Schema(description = "senha do usuario", example = "joão123")
    private String senha;

}
