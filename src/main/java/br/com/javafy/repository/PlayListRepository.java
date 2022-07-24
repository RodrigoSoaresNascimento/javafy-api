package br.com.javafy.repository;

import br.com.javafy.entity.PlayListEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface PlayListRepository extends JpaRepository<PlayListEntity, Integer> {

}
