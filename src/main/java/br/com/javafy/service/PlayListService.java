package br.com.javafy.service;

import br.com.javafy.dto.UsuarioDTO;
import br.com.javafy.dto.playlist.PlayListCreate;
import br.com.javafy.dto.playlist.PlayListDTO;
import br.com.javafy.dto.playlist.PlayListUpdate;
import br.com.javafy.dto.spotify.MusicaDTO;
import br.com.javafy.entity.MusicaEntity;
import br.com.javafy.entity.PlayListEntity;
import br.com.javafy.entity.UsuarioEntity;
import br.com.javafy.enums.TiposdePlano;
import br.com.javafy.exceptions.PessoaNaoCadastradaException;
import br.com.javafy.exceptions.PlaylistException;
import br.com.javafy.repository.PlayListRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PlayListService {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PlayListRepository playListRepository;

    @Autowired
    private PlayListMusicaService playListMusicaService;

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

    public PlayListUpdate converterParaPlaylistUpdate (PlayListEntity playList){
        return objectMapper.convertValue(playList, PlayListUpdate.class);
    }

    public UsuarioEntity validUser(Integer idUsuario) throws SQLException, PessoaNaoCadastradaException {
        UsuarioDTO usuarioDTO = usuarioService.findById(idUsuario);
        if(usuarioDTO.getPlano().equals(TiposdePlano.FREE)){
            throw new PessoaNaoCadastradaException("Plano Free. Para criar playlist, mude seu plano.");
        }
        return objectMapper.convertValue(usuarioDTO, UsuarioEntity.class);
    }

    public void validPlaylist(Integer idPlaylist) throws SQLException {
//        PlayList playList = playListRepository.getPlaylistById(idPlaylist);
//
//        if(playList.getIdPlaylist() == null){
//            throw new PlayListException("Id playlist não encontado. Id " + idPlaylist );
//        }
        return;

    }

    public PlayListDTO getPlaylistById (Integer idPlayList) throws  PlaylistException {
        return converterParaPlaylistDTO(retornaPlaylistEntityById(idPlayList));
    }

    public PlayListDTO create (PlayListCreate playlistCreate, Integer idUsuario)
            throws PessoaNaoCadastradaException, PlaylistException {

        UsuarioEntity usuario = usuarioService.retornaUsuarioEntityById(idUsuario);
        PlayListEntity playList = converterParaPlaylist(playlistCreate);
        playList.setUsuario(usuario);
        if(playlistCreate.getMusicas() != null) {
            Set<MusicaEntity> setMusicaEntity = playlistCreate.getMusicas()
                    .stream()
                    .map(MusicaDTO::getIdMusica)
                    .collect(Collectors.toSet())
                    .stream()
                    .map(idMusica -> new MusicaEntity(idMusica, Set.of()))
                    .collect(Collectors.toSet());
            setMusicaEntity.forEach(System.out::println);
            playList.setMusicas(setMusicaEntity);
        }
        playList = playListRepository.save(playList);

        return converterParaPlaylistDTO(playList);

//        Usuario usuario = validUser(idUsuario);
//
//        PlayList playList = converterParaPlaylist(playlistCreate);
//        playList.setUsuario(usuario);
//        playList = playListRepository.create(playList);
//
//        if(playList.getIdPlaylist() == null) {
//            throw new PlayListException("Erro ao salvar playlist.");
//        }
//
//        List<MusicaDTO> musicas =
//                playListMusicaService.addMusicaPlaylist(playlistCreate, playList.getIdPlaylist());
//
//        PlayListDTO playListDTO = converterParaPlaylistDTO(playList);
//        playListDTO.setMusicas(musicas);
//        return playListDTO;
    }

    public PlayListUpdate update(PlayListCreate playListCreate, Integer idPlaylist)
            throws  SQLException {

          PlayListEntity playList  = retornaPlaylistEntityById(idPlaylist);



//
//        PlayList playList = converterParaPlaylist(playListCreate);
//        boolean result = playListRepository.update(idPlaylist, playList);
//
//        if(!result){
//            throw new PlayListException("Error ao atualizar playlist. ID " + idPlaylist);
//        }
//
//        playList.setIdPlaylist(idPlaylist);
//
//        return converterParaPlaylistUpdate(playList);
        return null;
    }

    public void delete (Integer idPlaylist) throws PessoaNaoCadastradaException, SQLException {
//        validPlaylist(idPlaylist);
//
//        if(!playListRepository.delete(idPlaylist)){
//            throw new PlayListException("Error ao deletar. Verifique o ID.");
//        }
        return;
    }


}
