package br.com.javafy.entity;

import br.com.javafy.enums.CargosEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "cargo")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CargoEntity implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ID_CARGO")
    @SequenceGenerator(name = "SEQ_ID_CARGO", sequenceName = "seq_id+cargo", allocationSize = 1)
    private Integer idCargo;

    @Enumerated(EnumType.ORDINAL)
    private CargosEnum nome;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "usuario_cargo",
            joinColumns = @JoinColumn(name = "id_cargo"),
            inverseJoinColumns = @JoinColumn(name = "id_user")
    )
    Set<UsuarioEntity> usuarios;

    @Override
    public String getAuthority() {
        return "";
    }

    @Override
    public String toString() {
        return "CargoEntity{" +
                "idCargo=" + idCargo +
                ", nome='" + nome + '\'' +
                '}';
    }
}
