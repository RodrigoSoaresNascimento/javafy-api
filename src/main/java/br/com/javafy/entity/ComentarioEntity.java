package br.com.javafy.entity;

import lombok.*;

import javax.persistence.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "comentario")
public class ComentarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_COMENTARIO")
    @SequenceGenerator(name = "SEQ_COMENTARIO", sequenceName = "seq_id_comentario", allocationSize = 1)
    @Column(name = "id_comentario")
    private Integer idComentario;

    @Column(name = "comentario")
    private String comentario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_playlist",
            referencedColumnName = "id_playlist")
    private PlayListEntity playList;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user",
            referencedColumnName = "id_user")
    private UsuarioEntity usuarioEntity;

}
