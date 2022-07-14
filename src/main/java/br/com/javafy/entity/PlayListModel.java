package br.com.javafy.entity;

import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PlayListModel<T> {
    @NotNull
    @Size(min = 1, max = 9)
    private Integer idPlaylist;

    @NotNull
    @Size(min = 1, max = 60)
    private String nome;

    private T proprietario;
    private List<Musica> musicas = new ArrayList<>();
}
