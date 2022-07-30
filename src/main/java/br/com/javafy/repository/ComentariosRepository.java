package br.com.javafy.repository;

import br.com.javafy.dto.comentario.ComentarioPlaylistRelatorioDTO;
import br.com.javafy.entity.ComentarioEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComentariosRepository extends JpaRepository<ComentarioEntity, Integer> {
    @Query(" select new br.com.javafy.dto.comentario.ComentarioPlaylistRelatorioDTO(" +
            " c.comentario," +
            " p.name," +
            " u.nome," +
            " u.email" +
            ")" +
            "  from comentario c " +
            "  inner join c.playList p" +
            "  inner join c.usuarioEntity u ")
    List<ComentarioPlaylistRelatorioDTO> relatorioComentarios();

    //todo -> mudei o nome conforme correção passada no trabalho anterior
    //Page<ComentarioEntity> comentarioPaginadoPorId (Integer idComentario, PageRequest pageRequest);

}
