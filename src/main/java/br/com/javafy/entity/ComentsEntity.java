package br.com.javafy.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Id;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "coments")
public class ComentsEntity {

    @Id
    @Field(name = "_id")
    private String idComents;

    @Field(name = "idPost")
    private String idPosts;

    @Field(name = "idUsuario")
    private Integer idUsuario;

    @Field(name = "body")
    private String body;
}
