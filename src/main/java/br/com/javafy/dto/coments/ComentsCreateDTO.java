package br.com.javafy.dto.coments;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComentsCreateDTO {

    @Schema(description = "Post para comentário")
    private String idPosts;
    @Schema(description = "Corpo do comentário")
    private String body;
}

