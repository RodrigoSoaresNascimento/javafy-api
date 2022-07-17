package br.com.javafy.dto;

import br.com.javafy.enums.TiposdePlano;
import lombok.*;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UsuarioCreateDTO {

    @NotBlank(message = "Nome não pode ser nulo.")
    private String nome;

    @NotNull
    @Past
    private LocalDate dataNascimento;

    @Size(min = 1, max = 20)
    private String genero;

    @NotNull
    private TiposdePlano plano;

    @NotBlank(message = "Email e obrigatorio")
    private String email;

}
