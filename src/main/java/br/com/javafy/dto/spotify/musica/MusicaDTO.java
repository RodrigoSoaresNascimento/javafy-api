package br.com.javafy.dto.spotify.musica;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MusicaDTO {

    @JsonProperty("id")
    private String idMusica;

    @JsonProperty("name")
    private String nome;

    @JsonProperty("duration_ms")
    private Integer duracaoMs;

    @JsonProperty("popularity")
    private Integer popularidade;

    @JsonProperty("preview_url")
    private String urlMusica;

}

