package br.com.javafy.entity;

import lombok.*;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public abstract class Usuario {
    @NotNull
    @Size(min = 1, max = 9)
    private Integer idUser;

    @NotBlank(message = "Nome não pode ser nulo")
    private String nome;

    @NotNull
    @Past
    private LocalDate dataNascimento;

    @Size(min = 1, max = 20)
    private String genero;

    @NotNull
    @Size(min = 1, max = 1)
    private Integer premium;

    @Email
    @NotBlank(message = "Email é obrigatorio")
    private String email;

    //private List<Usuario> seguidores = new ArrayList<>();
    //private List<Usuario> seguindo = new ArrayList<>();

}
