package br.com.javafy.dto.coments;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ComentsDTO extends ComentsCreateDTO{

    @Schema(description = "Id do comentário")
    private String idComents;
}
