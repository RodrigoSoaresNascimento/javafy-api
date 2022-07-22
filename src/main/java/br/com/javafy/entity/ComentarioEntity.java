package br.com.javafy.entity;

import lombok.*;

import javax.persistence.*;


@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "comentario")
public class ComentarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_COMENTARIO")
    @SequenceGenerator(name = "SEQ_COMENTARIO", sequenceName = "seq_id_comentario", allocationSize = 1)
    @Column(name = "id_comentario")
    private Integer idComentario;

    @Column(name = "id_user")
    private Integer idUser;

    @Column(name = "comentario")
    private String comentario;

    @Column(name = "id_playlist")
    private Integer idPlaylist;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_playlist",
            referencedColumnName = "id_playlist")
    private PlayListEntity playList;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario",
            referencedColumnName = "id_usuario")
    private UsuarioEntity usuarioEntity;
}
