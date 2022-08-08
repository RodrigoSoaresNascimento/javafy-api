package br.com.javafy.repository;

import br.com.javafy.entity.PostsEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostsRepository extends MongoRepository<PostsEntity, String> {
}

