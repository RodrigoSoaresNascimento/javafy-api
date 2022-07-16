package br.com.javafy.service.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UsuarioDTO extends UsuarioCreateDTO {

    private Integer idUsuario;
}
