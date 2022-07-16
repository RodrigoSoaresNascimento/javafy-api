package br.com.javafy.entity;

import br.com.javafy.enums.TiposdePlano;
import lombok.*;
import org.springframework.boot.context.properties.bind.DefaultValue;

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
public class Usuario {
    @NotNull
    @Size(min = 1, max = 9)
    private Integer idUsuario;

    @NotBlank(message = "Nome não pode ser nulo")
    private String nome;

    @NotNull
    @Past
    private LocalDate dataNascimento;

    @Size(min = 1, max = 20)
    private String genero;

    @NotNull
    private TiposdePlano plano;

    @Email
    @NotBlank(message = "Email é obrigatorio")
    private String email;

    private List<PlayList> playLists = new ArrayList<>();

    //private List<Usuario> seguidores = new ArrayList<>();
    //private List<Usuario> seguindo = new ArrayList<>();

}
