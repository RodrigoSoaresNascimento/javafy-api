package br.com.javafy.controller;


import br.com.javafy.documentation.DocumentationPlaylist;
import br.com.javafy.dto.PageDTO;
import br.com.javafy.dto.playlist.PlayListCreate;
import br.com.javafy.dto.playlist.PlayListDTO;
import br.com.javafy.dto.playlist.PlayListUpdate;
import br.com.javafy.dto.playlist.PlaylistAddMusicaDTO;
import br.com.javafy.exceptions.PessoaNaoCadastradaException;
import br.com.javafy.exceptions.PlaylistException;
import br.com.javafy.exceptions.SpotifyException;
import br.com.javafy.service.PlayListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/playlist")
@Validated
public class PlaylistController implements DocumentationPlaylist{

    @Autowired
    private PlayListService playListService;

    @GetMapping("/filtrada-por-id-com-musicas/{idPlaylist}")
    public ResponseEntity<PlayListDTO> getPlaylistWithIdWithMusics(@PathVariable Integer idPlaylist)
            throws PlaylistException, SpotifyException {
        return ResponseEntity.ok(playListService.getPlaylistWithIdWithMusics(idPlaylist));
    }

    @GetMapping("/filtrada-por-id-sem-musicas/{idPlaylist}")
    public ResponseEntity<PlayListDTO> getPlaylistWithIdWithNotMusics(@PathVariable Integer idPlaylist)
            throws PlaylistException {

        return ResponseEntity.ok(playListService.getPlaylistWithIdWithNotMusics(idPlaylist));
    }

    @GetMapping("/list")
    public PageDTO<PlayListDTO> getListPlayList(Integer pagina, Integer qtRegistro)
            throws PlaylistException {
        return playListService.getListPlayList(pagina, qtRegistro);
    }

    @Override
    @PostMapping("/{idUsuario}")
    public PlayListDTO create(@Valid @RequestBody PlayListCreate playListCreate, Integer idUsuario)
            throws PlaylistException, PessoaNaoCadastradaException, SpotifyException {
        return playListService.create(playListCreate, idUsuario);
    }

    @Override
    @PutMapping("/{idPlaylist}")
    public ResponseEntity<PlayListDTO> update(
            @PathVariable Integer idPlaylist,
            @Valid @RequestBody PlaylistAddMusicaDTO playlist) throws PlaylistException, SpotifyException {

        return ResponseEntity.ok(playListService.update(idPlaylist, playlist));
    }

    @DeleteMapping("/{idPlayList}")
    public void delete(Integer idPlayList) throws SQLException, PessoaNaoCadastradaException
             {
        playListService.delete(idPlayList);
    }

    @DeleteMapping("/{idPlayList}/musica/{idMusica}")
    public void removeMusica(@PathVariable Integer idPlayList,@PathVariable String idMusica)
            throws PlaylistException {
        playListService.removerMusica(idPlayList, idMusica);
    }


}
