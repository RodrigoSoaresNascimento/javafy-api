package br.com.javafy.service;

import br.com.javafy.dto.playlist.PlayListCreate;
import br.com.javafy.dto.playlist.PlayListDTO;
import br.com.javafy.dto.playlist.PlayListUpdate;
import br.com.javafy.dto.spotify.musica.MusicaFullDTO;
import br.com.javafy.entity.PlayListEntity;
import br.com.javafy.entity.PlaylistMusicaEntity;
import br.com.javafy.entity.UsuarioEntity;
import br.com.javafy.entity.pk.PlaylistMusicaPK;
import br.com.javafy.exceptions.PessoaNaoCadastradaException;
import br.com.javafy.exceptions.PlaylistException;
import br.com.javafy.exceptions.SpotifyException;
import br.com.javafy.repository.PlayListRepository;
import br.com.javafy.repository.PlaylistMusicaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PlayListService {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PlayListRepository playListRepository;

    @Autowired
    private PlaylistMusicaRepository playlistMusicaRepository;

    @Autowired
    private PlayListMusicaService playListMusicaService;

    @Autowired
    private MusicaService musicaService;

    @Autowired
    private UsuarioService usuarioService;

    public PlayListDTO converterParaPlaylistDTO (PlayListEntity playList){
        return objectMapper.convertValue(playList,PlayListDTO.class);
    }

    public PlayListEntity converterParaPlaylist (PlayListCreate playList){
        return objectMapper.convertValue(playList, PlayListEntity.class);
    }

    public PlayListEntity retornaPlaylistEntityById(Integer id) throws PlaylistException {
        return playListRepository
                .findById(id)
                .orElseThrow(() -> new PlaylistException("Playlist Não Cadastrada"));
    }

    public PlayListDTO getPlaylistWithIdWithMusics (Integer idPlayList) throws PlaylistException, SpotifyException {
        return null;
//        return retornarPlaylistComMusicas(
//                retornaPlaylistEntityById(idPlayList),
//                "buscar por id"
//        );
    }

    public PlayListDTO getPlaylistWithIdWithNotMusics (Integer idPlayList) throws PlaylistException {
        return converterParaPlaylistDTO(retornaPlaylistEntityById(idPlayList));
    }

    public List<PlayListDTO> getListPlayList () throws PlaylistException {
        return playListRepository.findAll()
                .stream()
                .map(this::converterParaPlaylistDTO)
                .toList();
    }


    public PlayListDTO create (PlayListCreate playlistCreate, Integer idUsuario)
            throws PessoaNaoCadastradaException, PlaylistException, SpotifyException {
        UsuarioEntity usuario = usuarioService.retornaUsuarioEntityById(idUsuario);
        PlayListEntity playList = converterParaPlaylist(playlistCreate);
        playList.setUsuario(usuario);

        playList = playListRepository.save(playList);

        List<MusicaFullDTO> musicaDTOList = new ArrayList<>();
        Set<PlaylistMusicaEntity> listMusicas = new HashSet<>();

        if(playlistCreate.getMusicas() != null) {
            PlayListEntity finalPlayList = playList;
            musicaDTOList = musicaService.getMusicasValidas(playlistCreate.getMusicas());

            listMusicas = musicaService
                    .getMusicasValidas(playlistCreate.getMusicas())
                    .stream()
                    .map(m -> new
                            PlaylistMusicaEntity(new PlaylistMusicaPK(m.getIdMusica(), finalPlayList))
                    )
                    .collect(Collectors.toSet());
        }

        playList.setListaMusica(listMusicas);
        playListRepository.save(playList);

        PlayListDTO playListDTO = converterParaPlaylistDTO(playList);
        playListDTO.setMusicas(musicaDTOList);
        return playListDTO;

    }

    public PlayListDTO update(PlayListCreate playListCreate, Integer idPlaylist) throws PlaylistException, SpotifyException {

        PlayListEntity playList  = retornaPlaylistEntityById(idPlaylist);
        playList.setName(playListCreate.getName());


        List<MusicaFullDTO> musicaDTOList = new ArrayList<>();
        Set<PlaylistMusicaEntity> listMusicas = new HashSet<>();
        playList = playListRepository.save(playList);

        if(playListCreate.getMusicas() != null) {
            PlayListEntity finalPlayList = playList;
            musicaDTOList = musicaService.getMusicasValidas(playListCreate.getMusicas());

            listMusicas =
                    musicaDTOList
                    .stream()
                    .map(m -> new
                            PlaylistMusicaEntity(new PlaylistMusicaPK(m.getIdMusica(), finalPlayList))
                    )
                    .collect(Collectors.toSet());
        }

        listMusicas.addAll(playList.getListaMusica());
        playlistMusicaRepository.saveAll(listMusicas);


        //PlayListDTO playListDTO = converterParaPlaylistDTO(playList);
        //String ids = getIdsForListMusic(playList);
        //playListDTO.setMusicas(musicaService.getListMusicaPorIds(ids));

        //return playListDTO;
        return null;
    }

    public PlayListDTO updateAddMusica(Integer idPlaylist, PlayListUpdate playListUpdate) throws PlaylistException, SpotifyException {
        PlayListEntity playList = retornaPlaylistEntityById(idPlaylist);


        List<MusicaFullDTO> musicaDTOList = new ArrayList<>();
        Set<PlaylistMusicaEntity> listMusicas = new HashSet<>();

        if(playListUpdate.getMusicas() != null) {
            PlayListEntity finalPlayList = playList;
            musicaDTOList = musicaService.getMusicasValidas(playListUpdate.getMusicas());

            listMusicas =
                    musicaDTOList
                            .stream()
                            .map(m -> new
                                    PlaylistMusicaEntity(new PlaylistMusicaPK(m.getIdMusica(), finalPlayList))
                            )
                            .collect(Collectors.toSet());
        }

        playlistMusicaRepository.saveAll(listMusicas);

        return null;
    }

    public void removerMusica (Integer idPlaylist, String idMusica) throws PlaylistException {

        PlayListEntity playList = retornaPlaylistEntityById(idPlaylist);

        Optional<PlaylistMusicaEntity> playlistOption =
            playlistMusicaRepository.findById(new PlaylistMusicaPK(idMusica, playList));

        playlistMusicaRepository.delete(playlistOption.orElseThrow(()-> new PlaylistException("Error ao remover musica")));

    }

    public void delete (Integer idPlaylist) throws PessoaNaoCadastradaException, SQLException {
        PlayListEntity playList = retornaPlaylistEntityById(idPlaylist);
        playlistMusicaRepository.deleteAll(playList.getListaMusica());
        playListRepository.delete(playList);

    }



//    private void addMusicaInPlaylist(List<MusicaCreateDTO> playListCreate, PlayListEntity playList) throws SpotifyException {
//        Set<MusicaEntity> musicasEntity;
//        musicasEntity= musicaService
//                .getMusicasValidas(playListCreate)
//                .stream()
//                .map(m -> new MusicaEntity(m.getIdMusica(), Set.of()))
//                .collect(Collectors.toSet());
//        musicasEntity.addAll(playList.getMusicas());
//        playList.setMusicas(musicasEntity);
//    }
//
//    private PlayListDTO retornarPlaylistComMusicas(PlayListEntity playList, String tipoDeServico) throws PlaylistException, SpotifyException {
//
//        try {
//            playList = playListRepository.save(playList);
//        } catch (Exception e){
//            throw new PlaylistException("Error ao " + tipoDeServico + " a playlist.");
//        }
//
//        PlayListDTO playListDTO = converterParaPlaylistDTO(playList);
//        //String ids = getIdsForListMusic(playList);
//        //playListDTO.setMusicas(musicaService.getListMusicaPorIds(ids));
//
//        return playListDTO;
//    }
//
//    private String getIdsForListMusic(PlayListEntity playList) {
//        return playList.getMusicas()
//                .stream()
//                .map(MusicaEntity::getIdMusica)
//                .collect(Collectors.joining(","));
//    }

}
