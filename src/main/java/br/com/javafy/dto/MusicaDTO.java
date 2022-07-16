package br.com.javafy.dto;
import br.com.javafy.entity.Musica;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class MusicaDTO {
    @Schema(description = "Id da musica conforme o Spotify")
    private String idMusica;
    @Schema(description = "Id do album")
    private String idAlbum;
    @Schema(description = "Nome da musica conforme o Spotify")
    private String nome;
    @Schema(description = "Avaliacao da musica conforme o Spotify")
    private Integer avaliacao;
    @Schema(description = "Duracao da musica conforme o Spotify")
    private Integer duracao;
    @Schema(description = "Curtidas da musica conforme o Spotify")
    private Integer curtidas;

}