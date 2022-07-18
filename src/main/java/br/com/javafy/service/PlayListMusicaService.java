package br.com.javafy.service;

import br.com.javafy.dto.playlist.PlayListCreate;
import br.com.javafy.dto.spotify.MusicaDTO;
import br.com.javafy.dto.spotify.MusicaFullDTO;
import br.com.javafy.entity.Musica;
import br.com.javafy.exceptions.BancoDeDadosException;
import br.com.javafy.repository.PlayListMusicaRespository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PlayListMusicaService {

    @Autowired
    private PlayListMusicaRespository playListMusicaRespository;

    @Autowired
    private MusicaService musicaService;

    @Autowired
    ObjectMapper objectMapper;

    public boolean validIfPlaylistIsValid(PlayListCreate playlistCreate){
        return playlistCreate.getMusicas() != null && !playlistCreate.getMusicas().isEmpty();
    }

    public List<MusicaDTO> addMusicaPlaylist(PlayListCreate playlistCreate, Integer idPlaylist) throws SQLException {
        List<MusicaDTO> listaMusica = new ArrayList<>();
        Set<String> musicasAdd = new HashSet<>();

        if(validIfPlaylistIsValid(playlistCreate)){

            for(MusicaDTO music: playlistCreate.getMusicas()){
                try {
                    if(musicasAdd.contains(music.getId())){
                        continue;
                    }

                    boolean result = playListMusicaRespository
                            .create(idPlaylist, music.getId());

                    if(result){
                        MusicaFullDTO musicaFullDTO = musicaService.musicById(music.getId());
                        listaMusica.add(objectMapper.convertValue(musicaFullDTO, MusicaDTO.class));
                        musicasAdd.add(music.getId());
                    }

                }catch (Exception e) {

                }
            }
        }
        return listaMusica;
    }

    public List<MusicaDTO> getMusicasPlaylist(Integer idPlaylist) throws BancoDeDadosException {
        return playListMusicaRespository
                .getIdMusica(idPlaylist)
                .stream().map(
                        s -> {
                            return musicaService.musicById(s);
                        }
                ).collect(Collectors.toList());
    }

}
