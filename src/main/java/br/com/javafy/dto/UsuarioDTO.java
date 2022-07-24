package br.com.javafy.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UsuarioDTO extends UsuarioCreateDTO {

    @NotNull
    @Size(min = 1, max = 9)
    @Schema(description = "identificador unico do usuario")
    private Integer idUsuario;

}
