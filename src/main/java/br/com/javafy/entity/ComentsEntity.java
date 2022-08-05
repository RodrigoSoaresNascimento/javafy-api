package br.com.javafy.entity;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Document(collation = "coments")
public class ComentsEntity {

    @Id
    @Field(name = "_id")
    private String idComents;

    @Field(name = "idUsuario")
    private Integer idUsuario;

    @Field(name = "body")
    private String body;
}
