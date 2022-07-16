package br.com.javafy.anotations;

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
                @ApiResponse(responseCode = "200", description = "Operação concluída com sucesso."),
                @ApiResponse(responseCode = "401", description = "Ação não autorizado."),
                @ApiResponse(responseCode = "400", description = "Bad Request. Verifique seus parâmetros"),
                @ApiResponse(responseCode = "404", description = "Página não encontrada. Verifique a endpoint."),
                @ApiResponse(responseCode = "500", description = "Error interno.")
        }
)
public @interface MagiaResponse {
}
