package br.com.javafy.dto.usuario;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;

public class UsuarioLoginDTO {


    @Schema(description = "login do usuario", example = "joão")
    @NotBlank
    private String login;

    @Schema(example = "duda@gmail.com")
    @NotBlank
    private String email;

}
