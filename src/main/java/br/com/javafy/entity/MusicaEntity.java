package br.com.javafy.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "musica")
public class MusicaEntity {

    @Id
    @Column(name = "id_musica")
    private String idMusica;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "musicas")
//    @JoinTable(name = "playlist_musica",
//            joinColumns = @JoinColumn(name="id_musica"),
//            inverseJoinColumns = @JoinColumn(name="id_playlist")
//    )
    private Set<PlayListEntity> playLists;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MusicaEntity that = (MusicaEntity) o;
        return Objects.equals(idMusica, that.idMusica);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idMusica);
    }

    @Override
    public String toString() {
        return "MusicaEntity{" +
                "idMusica='" + idMusica + '\'' +
                '}';
    }
}
