package br.com.javafy.documentation;

import br.com.javafy.anotations.MagiaResponse;
import br.com.javafy.dto.spotify.MusicaDTO;
import br.com.javafy.service.MusicaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public interface DocumentationMusica {

    @Operation(summary = "Exibir uma musica", description = "Exibir uma musica especificada por Id.")
    @MagiaResponse
    public MusicaDTO musicById(@PathVariable("id") String id);

    @Operation(summary = "Retonar uma lista de musica.")
    @MagiaResponse
    public List<MusicaDTO> getList();

    @Operation(summary = "Retonar uma lista de musica pesquisada.")
    @MagiaResponse
    public Map searchMusic();

}
