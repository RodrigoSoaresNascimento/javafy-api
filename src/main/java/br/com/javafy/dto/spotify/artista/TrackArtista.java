package br.com.javafy.dto.spotify.artista;

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
public class TrackArtista {

    @JsonProperty("artists")
    private List<ArtistaDTO> artistas;

    @JsonProperty("id")
    public String id;

    @JsonProperty("name")
    public String name;

    @JsonProperty("popularity")
    public int popularity;

    @JsonProperty("duration_ms")
    public int durationMs;

    @JsonProperty("preview_url")
    public String previewUrl;

}
