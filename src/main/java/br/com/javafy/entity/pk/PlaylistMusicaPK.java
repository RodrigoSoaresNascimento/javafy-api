package br.com.javafy.entity.pk;

import br.com.javafy.entity.MusicaEntity;
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

    @Column(name = "id_playlist")
    private PlayListEntity playList;

    @Column(name = "id_musica")
    private MusicaEntity musica;

}
