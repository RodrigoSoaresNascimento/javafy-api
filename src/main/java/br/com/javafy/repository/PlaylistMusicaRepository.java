package br.com.javafy.repository;

import br.com.javafy.entity.PlaylistMusicaEntity;
import br.com.javafy.entity.pk.PlaylistMusicaPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaylistMusicaRepository extends JpaRepository<PlaylistMusicaEntity, PlaylistMusicaPK> {
}
