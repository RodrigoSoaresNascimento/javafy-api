package br.com.javafy.dto.playlist;

import br.com.javafy.dto.spotify.musica.MusicaFullDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PlayListDTO {

    @NotNull
    private Integer idPlaylist;

    @NotEmpty
    @Schema(example = "Para ouvir pedalando")
    private String name;

    List<MusicaFullDTO> musicas;

}
