package br.com.javafy.dto.playlist;

import br.com.javafy.dto.UsuarioDTO;
import br.com.javafy.dto.spotify.MusicaDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayListCreate {

    @NotEmpty
    private String name;

    List<MusicaDTO> musicas;
}
