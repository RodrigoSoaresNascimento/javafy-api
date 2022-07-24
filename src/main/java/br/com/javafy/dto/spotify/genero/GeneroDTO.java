package br.com.javafy.dto.spotify.genero;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GeneroDTO {

    @JsonProperty("genres")
    private List<String> generos;
}
