package br.com.javafy.documentation;

import br.com.javafy.anotations.MagiaResponse;
import br.com.javafy.service.dto.spotify.MusicaDTO;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;

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
