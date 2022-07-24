package br.com.javafy.repository;


import br.com.javafy.entity.MusicaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MusicaRepository extends JpaRepository<MusicaEntity, String> {
}
