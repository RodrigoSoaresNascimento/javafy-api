package br.com.javafy.dto.usuario;

import br.com.javafy.enums.TiposdePlano;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UsuarioDTO extends UsuarioCreateDTO {

    @NotNull
    private Integer idUsuario;

    @JsonIgnore
    @Schema(hidden = true)
    @NotBlank
    private String senha;

    @JsonIgnore
    private String login;

}
