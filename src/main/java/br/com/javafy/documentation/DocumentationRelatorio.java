package br.com.javafy.documentation;

import br.com.javafy.anotations.MagiaResponse;
import br.com.javafy.dto.comentario.ComentarioPlaylistRelatorioDTO;
import br.com.javafy.dto.usuario.UsuarioRelatorioDTO;
import io.swagger.v3.oas.annotations.Operation;

import java.util.List;

public interface DocumentationRelatorio {

    @Operation(summary = "Retorna o relatorio dos comentarios feitos nas playlist")
    @MagiaResponse
    List<ComentarioPlaylistRelatorioDTO> relatorioComentarioPlaylist(Integer idUsuario);

    @Operation(summary = "Retorna um relatorio de cada playlist de um usuario")
    @MagiaResponse
    List<UsuarioRelatorioDTO> relatorioPlayList (Integer idUsuario);

}
