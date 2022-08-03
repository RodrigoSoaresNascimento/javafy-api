package br.com.javafy.dto.usuario;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioUpdateDTO {

    @Schema(example = "Juliana", description = "Nome do usuario")
    private String nome;

    @Schema(example = "2000-10-10", description = "Data de nascimento do usuario")
    @Past
    private LocalDate dataNascimento;

    @Schema(example = "F", description = "Genero do usuario")
    @Size(min = 1, max = 20)
    private String genero;

    @Schema(example = "faker@faker.com", description = "Email do usuario")
    private String email;

    @Override
    public String toString() {
        return "UsuarioUpdateDTO{" +
                "nome='" + nome + '\'' +
                ", dataNascimento=" + dataNascimento +
                ", genero='" + genero + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
