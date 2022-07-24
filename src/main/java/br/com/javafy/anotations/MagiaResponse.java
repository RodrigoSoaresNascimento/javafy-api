package br.com.javafy.anotations;

import br.com.javafy.exceptions.DefaultException;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@ApiResponses(
        value = {
                @ApiResponse(responseCode = "200",
                        description = "Operação concluída com sucesso."),
                @ApiResponse(responseCode = "401",
                        description = "Ação não autorizado.",
                        content = {@Content(schema =
                        @Schema(implementation = DefaultException.class)
                        )}
                ),
                @ApiResponse(responseCode = "400",
                        description = "Bad Request. Verifique seus parâmetros",
                        content = @Content(schema =
                        @Schema(implementation = DefaultException.class)
                        )
                ),
                @ApiResponse(responseCode = "404",
                        description = "Página não encontrada. Verifique a endpoint.",
                        content = @Content(schema =
                        @Schema(implementation = DefaultException.class)
                        )
                ),
                @ApiResponse(responseCode = "500",
                        description = "Error interno.",
                        content = @Content(schema =
                        @Schema(implementation = DefaultException.class)
                        )
                )
        }
)
public @interface MagiaResponse {
}
