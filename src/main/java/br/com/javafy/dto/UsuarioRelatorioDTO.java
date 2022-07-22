package br.com.javafy.dto;

import br.com.javafy.entity.PlayListEntity;
import br.com.javafy.enums.TiposdePlano;
import lombok.*;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UsuarioRelatorioDTO {

    private String nome;
    private String email;
    private TiposdePlano plano;
    private String nomePlaylist;
}
