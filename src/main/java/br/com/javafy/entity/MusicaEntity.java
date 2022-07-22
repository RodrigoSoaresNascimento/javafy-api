package br.com.javafy.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "musicas")
public class MusicaEntity {

    @Id
    @Column(name = "id_musica")
    private String idMusica;


    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinTable(name = "listademusicas",
            joinColumns = @JoinColumn(name = "id_musica"),
            inverseJoinColumns = @JoinColumn(name = "id_playlist")

    )
    private Set<MusicaEntity> musicas;

    @Override
    public String toString() {
        return "MusicaEntity{" +
                "idMusica='" + idMusica + '\'' +
                '}';
    }
}
