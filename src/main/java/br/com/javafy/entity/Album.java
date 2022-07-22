package br.com.javafy.entity;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Album {
    @NotNull
    @Size(min = 1, max = 9)
    private Integer idAlbum;
    //todo -> se der tempo fazer os endpoints
    private String biografia; // Não está presente no diagrama ER

    @Size(min = 1, max = 1)
    private Integer avaliacao;
}
