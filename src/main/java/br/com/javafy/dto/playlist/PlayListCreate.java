package br.com.javafy.dto.playlist;

import br.com.javafy.dto.spotify.musica.MusicaCreateDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayListCreate {

    @Schema(example = "Bota modão")
    @NotEmpty
    private String name;

    List<MusicaCreateDTO> musicas;
}
