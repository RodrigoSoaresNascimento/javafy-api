package br.com.javafy.dto.usuario;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO extends UsuarioCreateDTO {

    private Integer idUsuario;

    @JsonIgnore
    private String senha;

    @Override
    public String toString() {
        return "UsuarioDTO{" +
                "idUsuario=" + idUsuario +
                '}';
    }
}
