package br.com.javafy.entity;

import br.com.javafy.enums.TiposdePlano;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "usuario")
public class UsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_USER")
    @SequenceGenerator(name = "SEQ_USER", sequenceName = "seq_id_user", allocationSize = 1)
    @Column(name = "id_user")
    private Integer idUsuario;

    @Column(name = "nome")
    private String nome;

    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;

    @Column(name = "genero")
    private String genero;

    @Column(name = "premium")
    private TiposdePlano plano;
    @Column(name = "email")
    private String email;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "seguidores",
            joinColumns = @JoinColumn(name="id_user"),
            inverseJoinColumns = @JoinColumn(name = "id_user_seguindo")
    )
    private Set<UsuarioEntity> seguidores = new HashSet<>();

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "SEGUIDORES",
            joinColumns = @JoinColumn(name="id_user_seguindo"),
            inverseJoinColumns = @JoinColumn(name = "id_user")
    )
    private Set<UsuarioEntity> seguindo = new HashSet<>();


    @OneToMany(fetch = FetchType.LAZY,
                mappedBy = "usuario",
                cascade = CascadeType.ALL,
                orphanRemoval = true)
    private Set<PlayListEntity> playlist;

    @Override
    public String toString() {
        return "UsuarioEntity{" +
                "idUsuario=" + idUsuario +
                ", nome='" + nome + '\'' +
                ", dataNascimento=" + dataNascimento +
                ", genero='" + genero + '\'' +
                ", plano=" + plano +
                ", email='" + email + '\'';
    }
}
