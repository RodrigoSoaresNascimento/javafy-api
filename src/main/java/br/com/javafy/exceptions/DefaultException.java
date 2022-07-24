package br.com.javafy.exceptions;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class DefaultException {

    @Schema(example = "2019-01-17T16:12:45.977+0000")
    private String timestamp;

    @Schema(example = "400 || 401 || 404 || 500")
    private String status;

    @Schema(example = "Error processing the request!")
    private String message;
}
