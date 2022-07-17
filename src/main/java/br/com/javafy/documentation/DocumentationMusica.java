package br.com.javafy.documentation;

import br.com.javafy.anotations.MagiaResponse;
import br.com.javafy.dto.spotify.MusicaDTO;
import br.com.javafy.dto.spotify.MusicaFullDTO;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.util.List;

public interface DocumentationMusica {

    @Operation(summary = "Exibir uma musica", description = "Exibir uma musica especificada por Id.")
    @MagiaResponse
    public MusicaFullDTO musicById(@PathVariable("id") String id);

    @Operation(summary = "Retonar uma lista de musica.")
    @MagiaResponse
    public List<MusicaFullDTO> getList();

    @Operation(summary = "Retonar uma lista de musica pesquisada.")
    @MagiaResponse
    public List<MusicaDTO> searchMusic(@RequestBody String query) throws IOException;

}
