package br.com.javafy.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
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

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "id_playlist", referencedColumnName = "id_playlist")
    private Set<PlaylistMusicaEntity> listaMusica;


    @JsonIgnore
    @OneToMany(mappedBy = "playList",fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private Set<ComentarioEntity> comentarios;

    @Override
    public String toString() {
        return "PlayListEntity{" +
                "idPlaylist=" + idPlaylist +
                ", name='" + name + '\'' +
                '}';
    }

}
