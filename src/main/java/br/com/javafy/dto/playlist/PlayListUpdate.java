package br.com.javafy.dto.playlist;

import br.com.javafy.dto.spotify.musica.MusicaCreateDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayListUpdate {

    @Schema(required = true)
    List<MusicaCreateDTO> musicas;

}
