package br.com.javafy.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayList {

    private Integer idPlaylist;
    private Integer idUsuario;
    private String name;
    private List<Musica> musicas;

}
