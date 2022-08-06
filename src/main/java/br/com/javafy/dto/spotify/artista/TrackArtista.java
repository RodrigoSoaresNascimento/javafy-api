package br.com.javafy.dto.spotify.artista;

import br.com.javafy.dto.spotify.musica.MusicaDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TrackArtista {

    List<MusicaDTO> musicas;
//    @JsonProperty("artists")
//    private List<ArtistaDTO> artistas;
}
