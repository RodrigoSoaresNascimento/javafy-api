package br.com.javafy.entity;

import br.com.javafy.entity.pk.PlaylistMusicaPK;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "playlist_musica")
public class PlaylistMusicaEntity {

    @EmbeddedId
    private PlaylistMusicaPK playlistMusicaPK;

    @Override
    public String toString() {
        return "PlaylistMusicaEntity{" +
                "playlistMusicaPK=" + playlistMusicaPK +
                '}';
    }
}
