package br.com.javafy.dto.spotify;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArtistaDTO {

    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    private String nome;

}
