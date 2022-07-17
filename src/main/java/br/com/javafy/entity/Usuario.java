package br.com.javafy.entity;

import br.com.javafy.enums.TiposdePlano;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Usuario {

    private Integer idUsuario;
    private String nome;
    private LocalDate dataNascimento;
    private String genero;
    private TiposdePlano plano;
    private String email;
    private List<PlayList> playLists = new ArrayList<>();

    //private List<Usuario> seguidores = new ArrayList<>();
    //private List<Usuario> seguindo = new ArrayList<>();

}
