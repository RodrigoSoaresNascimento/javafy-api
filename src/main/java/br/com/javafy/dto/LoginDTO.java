package br.com.javafy.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class LoginDTO {

    @Schema(example = "user")
    @NotEmpty
    private String login;

    @NotEmpty
    private String senha;
}
