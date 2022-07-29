package br.com.javafy.dto;

import br.com.javafy.enums.TiposdePlano;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UsuarioLoginDTO {


    @Schema(description = "login do usuario", example = "joão")
    @NotBlank
    private String login;

    @Schema(example = "Duda")
    @NotBlank
    private String nome;

    @Schema(example = "duda@gmail.com")
    @NotBlank
    private String email;

    @Schema(example = "PREMIUM, FREE")
    @NotNull
    private TiposdePlano plano;

    @NotNull
    @Size(min = 1, max = 9)
    @Schema(description = "identificador unico do usuario")
    private Integer idUsuario;
}
