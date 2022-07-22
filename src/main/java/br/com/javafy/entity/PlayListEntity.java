package br.com.javafy.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user", referencedColumnName = "id_user")
    private UsuarioEntity usuario;

    @Override
    public String toString() {
        return "PlayListEntity{" +
                "idPlaylist=" + idPlaylist +
                ", name='" + name + '\'' +
                '}';
    }
}
