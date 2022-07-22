package br.com.javafy.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "playlist")
public class PlayListEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PLAYLIST")
    @SequenceGenerator(name = "SEQ_PLAYLIST", sequenceName = "seq_id_playlist", allocationSize = 1)
    @Column(name = "id_playlist")
    private Integer idPlaylist;

    @Column(name = "nome")
    private String name;

    @Column(name = "id_user")
    private Integer idUser;

}
