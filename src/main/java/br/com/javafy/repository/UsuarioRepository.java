package br.com.javafy.repository;

import br.com.javafy.dto.ComentarioPlaylistRelatorioDTO;
import br.com.javafy.dto.UsuarioRelatorioDTO;
import br.com.javafy.entity.UsuarioEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Integer> {

    @Query(" select new br.com.javafy.dto.UsuarioRelatorioDTO(" +
            " u.nome," +
            " u.email," +
            " u.plano," +
            " p.name" +
            ")" +
            "  from usuario u " +
            "  inner join u.playlist p " +
            " where (:idUsuario is null OR u.idUsuario = :idUsuario )")
    List<UsuarioRelatorioDTO> relatorioPessoa(@Param("idUsuario") Integer idPessoa);

    Page<UsuarioEntity> findUsuarioEntitiesByNome(String nome, PageRequest pageRequest);

}
