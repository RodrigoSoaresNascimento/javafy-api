package br.com.javafy.dto.spotify.musica;

import br.com.javafy.dto.spotify.artista.ArtistaDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MusicaFullDTO extends MusicaDTO{

    @JsonProperty("artists")
    private List<ArtistaDTO> artistas;

}
