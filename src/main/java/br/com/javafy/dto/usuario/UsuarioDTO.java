package br.com.javafy.dto.usuario;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO extends UsuarioCreateDTO {

    @NotNull
    private Integer idUsuario;

    @Override
    public String toString() {
        return "UsuarioDTO{" +
                "idUsuario=" + idUsuario +
                ", senha='" + getSenha() + '\'' +
                ", login='" + getLogin() + '\'' +
                '}';
    }
}
