package br.com.javafy.dto.playlist;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PlayListDTO extends PlayListCreate{

    @NotNull
    private Integer idPlaylist;

}
