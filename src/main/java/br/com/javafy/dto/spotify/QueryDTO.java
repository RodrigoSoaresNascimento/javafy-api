package br.com.javafy.dto.spotify;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryDTO {

    @NotBlank(message = "Query não deve ser vazio.")
    private String query;
}
