package br.com.javafy.dto.usuario;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UsuarioUpdateLoginDTO {

    @Schema(description = "login do usuario", example = "joão")
    @NotBlank
    private String login;

    @JsonIgnore
    @Schema(hidden = true)
    @NotBlank
    private String senha;

}
