package br.com.javafy.controller;

import br.com.javafy.dto.MusicaDTO;
import br.com.javafy.entity.Musica;
import br.com.javafy.service.MusicaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/musica")
@Validated
@Slf4j
public class MusicaController {
    @Autowired
    private MusicaService musicaService;

    @Operation(summary = "Exibir uma musica", description = "Exibir uma musica especificada por Id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a musica."),
                    @ApiResponse(responseCode = "500", description = "Erro de Servidor, foi gerada uma Exception."),
            }
    )
    @GetMapping("/{id}")
    public Musica musicaPorId(@PathVariable("id") String id) {
        return musicaService.getMusicaPorId(id);
    }

    @Operation(summary = "Listar musicas por nome", description = "Lista musicas por nome ou trechos de nome")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna as musicas que possuem o nome ou trecho informado."),
                    @ApiResponse(responseCode = "500", description = "Erro de Servidor, foi gerada uma Exception."),
            }
    )
    @GetMapping("/byname") // /pessoa/byname?nome=musica
    public List<Musica> musicaPorNome(@RequestParam("nome") String nome) {
        return musicaService.getMusicaPorNome(nome);
    }
    @Operation(summary = "Listar musicas por artista", description = "Lista musicas por artista")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna as musicas que possuem o artista informado."),
                    @ApiResponse(responseCode = "500", description = "Erro de Servidor, foi gerada uma Exception."),
            }
    )
    @GetMapping("/{artista}")
    public List<Musica> musicaPorArtista(@PathVariable("nome") String nome) {
        return musicaService.getMusicaPorNome(nome);
    }
}