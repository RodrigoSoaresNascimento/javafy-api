package br.com.javafy.entity;

import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Artista extends Usuario {
    @NotNull
    @Size(min = 1, max = 9)
    private Integer idArtista;

    @Size(min = 1, max = 500)
    private String bio;

    @Size(min = 1, max = 1)
    private Integer avaliacao;
}
