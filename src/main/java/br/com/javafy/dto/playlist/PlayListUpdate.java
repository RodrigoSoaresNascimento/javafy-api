package br.com.javafy.dto.playlist;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayListUpdate {

    @NotNull
    private Integer idPlaylist;

    @NotEmpty
    private String name;

}
