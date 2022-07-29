package br.com.javafy.repository;

import br.com.javafy.entity.CargoEntity;
import br.com.javafy.enums.CargosEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface CargoRepository extends JpaRepository<CargoEntity, Integer> {

    CargoEntity findByNome(CargosEnum cargosEnum);

}
