package br.com.javafy.dto.playlist;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayListCreate {

    @Schema(example = "Academia musicas")
    @NotEmpty
    private String name;

}
