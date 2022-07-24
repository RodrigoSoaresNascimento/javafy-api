package br.com.javafy.dto;

import br.com.javafy.enums.TiposdePlano;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UsuarioCreateDTO {

    @Schema(example = "Juliana")
    @NotBlank(message = "Nome não pode ser nulo.")
    private String nome;

    @Schema(example = "2000-10-10")
    @NotNull
    @Past
    private LocalDate dataNascimento;

    @Schema(example = "F")
    @Size(min = 1, max = 20)
    private String genero;

    @NotNull
    private TiposdePlano plano;

    @Schema(example = "faker@faker.com")
    @NotBlank(message = "Email e obrigatorio")
    private String email;

}
