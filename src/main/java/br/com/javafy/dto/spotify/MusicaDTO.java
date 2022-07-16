package br.com.javafy.dto.spotify;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MusicaDTO {

    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    private String nome;

//    @JsonProperty("popularity")
//    private Integer popular;

    @JsonProperty("artists")
    private List<ArtistaDTO> artistas;

}
