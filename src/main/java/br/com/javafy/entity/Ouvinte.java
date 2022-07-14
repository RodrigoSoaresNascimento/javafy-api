package br.com.javafy.entity;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Ouvinte extends Usuario{

    private Integer idOuvinte;

    public Ouvinte(Integer idUser, @NotBlank(message = "Nome não pode ser nulo") String nome, @NotNull @Past LocalDate dataNascimento, String genero, @NotNull Integer premium, @Email @NotBlank(message = "Email é obrigatorio") String email, Integer idOuvinte) {
        super(idUser, nome, dataNascimento, genero, premium, email);
        this.idOuvinte = idOuvinte;
    }
}
