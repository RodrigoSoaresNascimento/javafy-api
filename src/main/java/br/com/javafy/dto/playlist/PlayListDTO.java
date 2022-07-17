package br.com.javafy.dto.playlist;

import br.com.javafy.dto.spotify.MusicaDTO;
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
public class PlayListDTO extends PlayListCreate{

    @NotNull
    private Integer idPlayList;

}
