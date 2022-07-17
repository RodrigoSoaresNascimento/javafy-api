package br.com.javafy.dto.playlist;

import br.com.javafy.dto.spotify.MusicaDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayListCreate {

    private Integer idUsuario;

    @NotEmpty
    private String name;

    List<MusicaDTO> musicas;
}
