package br.com.javafy.repository;

import br.com.javafy.dto.usuario.UsuarioRelatorioDTO;
import br.com.javafy.entity.UsuarioEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Integer> {

    @Query(" select new br.com.javafy.dto.usuario.UsuarioRelatorioDTO(" +
            " u.nome," +
            " u.email," +
            " p.name" +
            ")" +
            "  from usuario u " +
            "  inner join u.playlist p ")
    List<UsuarioRelatorioDTO> relatorioPessoa();

    Page<UsuarioEntity> findUsuarioEntitiesByNome(String nome, PageRequest pageRequest);

    Optional<UsuarioEntity> findByLogin(String login);

    @Query("select u " +
            " from usuario u " +
            "where day(u.dataNascimento)= day(current_date()) " +
            "and month(u.dataNascimento)=month(current_date())")
    List<UsuarioEntity> findByBirthDay();
}
