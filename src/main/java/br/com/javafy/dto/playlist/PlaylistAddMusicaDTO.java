package br.com.javafy.dto.playlist;

import br.com.javafy.dto.spotify.musica.MusicaCreateDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PlaylistAddMusicaDTO {

    @Schema(example = "Bota modão", required = false)
    @NotEmpty
    private String name;

    @Schema(required = true)
    List<MusicaCreateDTO> musicas;
}
