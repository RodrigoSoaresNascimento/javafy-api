package br.com.javafy.dto.usuario;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioCreateDTO {

    @Schema(example = "Juliana", description = "nome do usuario")
    private String nome;

    @Schema(example = "2000-10-10", description = "data de nascimento do usuario")
    @Past
    private LocalDate dataNascimento;

    @Schema(example = "F", description = "genero do usuario")
    @Size(min = 1, max = 20)
    private String genero;

    @Schema(description = "login do usuario", example = "joão")
    private String login;

    @Schema(description = "login do usuario", example = "HeavyMet@1")
    private String senha;

    @Schema(example = "faker@faker.com", description = "email do usuario")
    private String email;

}
