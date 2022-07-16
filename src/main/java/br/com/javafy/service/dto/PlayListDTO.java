package br.com.javafy.service.dto;

import br.com.javafy.entity.Musica;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public class PlayListDTO {

    @Schema(description = "Nome da playlist do usuario")
    private String name;
    @Schema(description = "Lista de musicas de um usuario")
    private List<Musica> musicasList;
    @Schema(description = "Identificação unica do usuario")
    private Integer id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Musica> getMusicasList() {
        return musicasList;
    }

    public void setMusicasList(List<Musica> musicasList) {
        this.musicasList = musicasList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
