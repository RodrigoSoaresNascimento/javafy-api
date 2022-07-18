package br.com.javafy.service;

import br.com.javafy.dto.UsuarioDTO;
import br.com.javafy.dto.playlist.PlayListCreate;
import br.com.javafy.dto.playlist.PlayListDTO;
import br.com.javafy.dto.playlist.PlayListUpdate;
import br.com.javafy.dto.spotify.MusicaDTO;
import br.com.javafy.entity.PlayList;
import br.com.javafy.entity.Usuario;
import br.com.javafy.enums.TiposdePlano;
import br.com.javafy.exceptions.PessoaNaoCadastradaException;
import br.com.javafy.exceptions.PlayListException;
import br.com.javafy.repository.PlayListRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

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

    public PlayListDTO converterParaPlaylistDTO (PlayList playList){
        return objectMapper.convertValue(playList,PlayListDTO.class);
    }

    public PlayList converterParaPlaylist (PlayListCreate playList){
        return objectMapper.convertValue(playList,PlayList.class);
    }

    public PlayListUpdate converterParaPlaylistUpdate (PlayList playList){
        return objectMapper.convertValue(playList, PlayListUpdate.class);
    }

    public Usuario validUser(Integer idUsuario) throws SQLException, PessoaNaoCadastradaException {
        UsuarioDTO usuarioDTO = usuarioService.findById(idUsuario);
        if(usuarioDTO.getPlano().equals(TiposdePlano.FREE)){
            throw new PessoaNaoCadastradaException("Plano Free. Para criar playlist, mude seu plano.");
        }
        return objectMapper.convertValue(usuarioDTO, Usuario.class);
    }

    public void validPlaylist(Integer idPlaylist) throws SQLException, PlayListException {
        PlayList playList = playListRepository.getPlaylistById(idPlaylist);

        if(playList.getIdPlaylist() == null){
            throw new PlayListException("Id playlist não encontado. Id " + idPlaylist );
        }

    }

    public PlayListDTO getPlaylistById (Integer idPlayList) throws PessoaNaoCadastradaException,
            SQLException, PlayListException {
        PlayList playList = playListRepository.getPlaylistById(idPlayList);

        if(playList.getIdPlaylist() == null){
            throw new PlayListException("Playlist não existe. ID: " + idPlayList);
        }

        List<MusicaDTO> musicaDTOS= playListMusicaService
                .getMusicasPlaylist(playList.getIdPlaylist());

        PlayListDTO playListDTO = converterParaPlaylistDTO(playList);
        playListDTO.setMusicas(musicaDTOS);
        return playListDTO;
    }

    public PlayListDTO create (PlayListCreate playlistCreate,
                               Integer idUsuario) throws SQLException, PessoaNaoCadastradaException,
            PlayListException {
        Usuario usuario = validUser(idUsuario);

        PlayList playList = converterParaPlaylist(playlistCreate);
        playList.setUsuario(usuario);
        playList = playListRepository.create(playList);

        if(playList.getIdPlaylist() == null) {
            throw new PlayListException("Erro ao salvar playlist.");
        }

        List<MusicaDTO> musicas =
                playListMusicaService.addMusicaPlaylist(playlistCreate, playList.getIdPlaylist());

        PlayListDTO playListDTO = converterParaPlaylistDTO(playList);
        playListDTO.setMusicas(musicas);
        return playListDTO;
    }

    public PlayListUpdate update(PlayListCreate playListCreate, Integer idPlaylist)
            throws PlayListException, SQLException {
        validPlaylist(idPlaylist);

        PlayList playList = converterParaPlaylist(playListCreate);
        boolean result = playListRepository.update(idPlaylist, playList);

        if(!result){
            throw new PlayListException("Error ao atualizar playlist. ID " + idPlaylist);
        }

        playList.setIdPlaylist(idPlaylist);

        return converterParaPlaylistUpdate(playList);
    }

    public void delete (Integer idPlaylist) throws PessoaNaoCadastradaException, SQLException,
            PlayListException {
        validPlaylist(idPlaylist);

        if(!playListRepository.delete(idPlaylist)){
            throw new PlayListException("Error ao deletar. Verifique o ID.");
        }
    }


}
