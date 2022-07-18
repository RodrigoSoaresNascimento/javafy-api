package br.com.javafy.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Comentario {

    private Integer idComentario;
    private Integer idUser;
    private String comentario;
    private Integer idPlaylist;
}
