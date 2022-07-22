package br.com.javafy.service;

import br.com.javafy.dto.playlist.PlayListCreate;
import br.com.javafy.dto.spotify.MusicaDTO;
import br.com.javafy.exceptions.BancoDeDadosException;
import br.com.javafy.repository.MusicaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayListMusicaService {

    @Autowired
    private MusicaRepository playListMusicaRespository;

    @Autowired
    private MusicaService musicaService;

    @Autowired
    ObjectMapper objectMapper;

    public boolean validIfPlaylistIsValid(PlayListCreate playlistCreate){
        return playlistCreate.getMusicas() != null && !playlistCreate.getMusicas().isEmpty();
    }

    public void validIfMusicInPlaylist(PlayListCreate playListCreate) throws  BancoDeDadosException {
//        Musica musica = playListMusicaRespository.getMusicaInPlaylist(idPlaylist, idPlaylist);
//        if(musica.getId() == null) {
//            throw new PlayListException("Playlist id " + idPlaylist + " não tem a musica  id" + idMusic );
//        }
        return;
    }

//    public Set<ListaDeMusicaEntity> addMusicaPlaylist(PlayListCreate playlistCreate, Integer idPlaylist)
//            throws SQLException {
//
//        if(playlistCreate.getMusicas() == null){
//            return null;
//        };
//
//        return playlistCreate.getMusicas().stream()
//                .map(m-> new ListaDeMusicaEntity(new ListaDeMusicaPK(idPlaylist, m.getId())))
//                .collect(Collectors.toSet());
////
////
//        List<MusicaDTO> listaMusica = new ArrayList<>();
//        Set<String> musicasAdd = new HashSet<>();
//
//
//
//
//        if(validIfPlaylistIsValid(playlistCreate)){
//
//            for(MusicaDTO music: playlistCreate.getMusicas()){
//                try {
//                    if(musicasAdd.contains(music.getId())){
//                        continue;
//                    }
//
//                    boolean result = false;
////                            playListMusicaRespository
////                            .create(idPlaylist, music.getId());
//
//                    if(result){
//                        MusicaFullDTO musicaFullDTO = musicaService.musicById(music.getId());
//                        listaMusica.add(objectMapper.convertValue(musicaFullDTO, MusicaDTO.class));
//                        musicasAdd.add(music.getId());
//                    }
//
//                }catch (Exception e) {
//
//                }
//            }
//        }
//        return listaMusica;
    //}

    public List<MusicaDTO> getMusicasPlaylist(Integer idPlaylist) throws BancoDeDadosException {
//        return playListMusicaRespository
//                .getIdMusica(idPlaylist)
//                .stream().map(
//                        s -> {
//                            return musicaService.musicById(s);
//                        }
//                ).collect(Collectors.toList());
        return null;
    }



}
