package br.com.javafy.repository;

import br.com.javafy.entity.CargoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface CargoRepository extends JpaRepository<CargoEntity, Integer> {

    CargoEntity findByNome(String nome);
}
