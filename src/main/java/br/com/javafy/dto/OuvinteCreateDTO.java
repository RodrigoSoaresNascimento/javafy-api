package br.com.javafy.dto;

import lombok.*;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OuvinteCreateDTO {


    private Integer idUser;

    @NotBlank(message = "Nome não pode ser nulo")
    private String nome;

    @NotNull
    @Past
    private LocalDate dataNascimento;

    @Size(min = 1, max = 20)
    private String genero;

    @NotNull
    @Min(1)
    private Integer premium;

    @Email
    @NotBlank(message = "Email é obrigatorio")
    private String email;
}
