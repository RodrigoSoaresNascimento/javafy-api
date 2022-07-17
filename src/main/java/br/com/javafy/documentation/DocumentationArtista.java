package br.com.javafy.documentation;

import br.com.javafy.anotations.MagiaResponse;
import br.com.javafy.dto.spotify.ArtistaDTO;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.PathVariable;



import java.util.List;

public interface DocumentationArtista {

    @Operation(summary = "Exibir um artista", description = "Exibir um artista especificado por Id.")
    @MagiaResponse
    public ArtistaDTO artistById(@PathVariable("id") String id);

    @Operation(summary = "Retonar uma lista de artistas.")
    @MagiaResponse
    public List<ArtistaDTO> getArtists();

}
