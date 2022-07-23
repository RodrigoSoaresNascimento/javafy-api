package br.com.javafy.entity.pk;

import br.com.javafy.entity.PlayListEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class PlaylistMusicaPK implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "id_musica")
    private String idMusica;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name="id_playlist", referencedColumnName = "id_playlist")
    private PlayListEntity playList;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlaylistMusicaPK that = (PlaylistMusicaPK) o;
        return Objects.equals(idMusica, that.idMusica);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idMusica);
    }

    @Override
    public String toString() {
        return "PlaylistMusicaPK{" +
                // "idPlaylist=" + idPlaylist +
                ", idMusica='" + idMusica + '\'' +
                '}';
    }
}
