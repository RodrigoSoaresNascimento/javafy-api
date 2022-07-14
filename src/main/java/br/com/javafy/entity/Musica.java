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
public class Musica {
    @NotNull
    @Size(min = 1, max = 9)
    private Integer idMusica;

    private Album album;

    @Size(min = 1,max = 200)
    private String nome;

    @Size(min = 1, max = 1)
    private Integer avaliacao;

    @NotNull
    @Size(min = 1, max = 3)
    private Double duracao;
    @Size(min = 1, max = 9)
    private Integer curtidas;

    public void imprimirMusica() {
        String leftAlignFormat = "| %-5s | %-30s | %-15s %n";
        System.out.format(leftAlignFormat, "Id: " + getIdMusica(), "Musica: "
                + getNome(), "Curtidas: " + getCurtidas());
    }
}
