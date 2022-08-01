package br.com.javafy.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "usuario")
public class UsuarioEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_USER")
    @SequenceGenerator(name = "SEQ_USER", sequenceName = "seq_id_user", allocationSize = 1)
    @Column(name = "id_user", insertable = false, updatable = false)
    private Integer idUsuario;

    @Column(name = "nome")
    private String nome;

    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;

    @Column(name = "genero")
    private String genero;

    @Column(name = "email")
    private String email;

    @Column(name = "login")
    private String login;

    @Column(name = "senha")
    private String senha;

    @Column(name = "enable")
    private boolean enable;

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

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "usuarioEntity",
            orphanRemoval = true
    )
    private Set<ComentarioEntity> comentarios;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY) // traz quando solicitado
    @JoinColumn(name = "id_cargo", referencedColumnName = "id_cargo")
    private CargoEntity cargo; // contato.getPessoa()


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(cargo);
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsuarioEntity usuario = (UsuarioEntity) o;
        return Objects.equals(idUsuario, usuario.idUsuario);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUsuario);
    }

    @Override
    public String toString() {
        return "UsuarioEntity{" +
                "idUsuario=" + idUsuario +
                ", nome='" + nome + '\'' +
                ", dataNascimento=" + dataNascimento +
                ", genero='" + genero + '\'' +
                ", email='" + email + '\'';
    }
}
