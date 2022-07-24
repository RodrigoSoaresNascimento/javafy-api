package br.com.javafy.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ComentarioPlaylistRelatorioDTO {

    private String comentario;
    private String nomePlaylist;
    private String nomeUsuario;
    private String email;

}
