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
    @Column(name = "id_cargo")
    private Integer idCargo;

    @Column(name = "nome")
    @Enumerated(EnumType.STRING)
    private CargosEnum nome;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "cargo")
    private Set<UsuarioEntity> usuarios;


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
