package br.com.javafy.dto.usuario;

import br.com.javafy.enums.CargosEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.jetbrains.annotations.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CargoDTO {

    @NotNull
    @Schema(example = "FREE")
    private CargosEnum nome;

}
