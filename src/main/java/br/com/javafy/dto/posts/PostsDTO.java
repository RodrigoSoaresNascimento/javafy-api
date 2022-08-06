package br.com.javafy.dto.posts;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PostsDTO {


    @Schema(description = "Id do Usuário")
    private Integer idUsuario;
    @Schema(description = "Título do post")
    private String title;
    @Schema(description = "Corpo do post")
    private String body;
    @Schema(description = "Data do post")
    private LocalDate date;
    @Schema(description = "Imagem do post")
    private String image;
    @Schema(description = "Id do post")
    private String idPosts;
}
