package br.com.javafy.dto.posts;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class PostsCreateDTO {

    @Schema(description = "Título do post")
    private String title;
    @Schema(description = "Corpo do post")
    private String body;
    @Schema(description = "Imagem do post")
    private String image;
}
