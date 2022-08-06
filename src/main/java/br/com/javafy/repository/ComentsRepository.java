package br.com.javafy.repository;

import br.com.javafy.entity.ComentsEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComentsRepository extends MongoRepository<ComentsEntity, String> {
}

