package br.com.javafy.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Id;
import java.time.LocalDate;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Document(collection = "posts")
public class PostsEntity {

    @Id
    @Field(name = "_id")
    private String idPosts;

    @Field("idUsuario")
    private Integer idUsuario;

    @Field(name = "title")
    private String title;

    @Field(name = "body")
    private String body;

    @Field(name = "date")
    private LocalDate date;

    @Field(name = "image")
    private String image;

    @Field(name = "comments")
    @DBRef
    private List<ComentsEntity> coments;
}
