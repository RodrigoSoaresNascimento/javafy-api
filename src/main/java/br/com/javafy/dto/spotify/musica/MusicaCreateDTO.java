package br.com.javafy.dto.spotify.musica;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MusicaCreateDTO {

    @Schema(example = "0csbIHcIB2Xu0QmfmulXul")
    @JsonProperty("id")
    private String idMusica;

}
