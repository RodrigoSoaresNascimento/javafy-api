package br.com.javafy.service;

import br.com.javafy.dto.UsuarioDTO;
import br.com.javafy.dto.playlist.PlayListCreate;
import br.com.javafy.dto.playlist.PlayListDTO;
import br.com.javafy.dto.spotify.MusicaDTO;
import br.com.javafy.entity.Musica;
import br.com.javafy.entity.PlayList;
import br.com.javafy.enums.TiposdePlano;
import br.com.javafy.exceptions.PessoaNaoCadastradaException;
import br.com.javafy.repository.PlayListMusicaRespository;
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
    private PlayListMusicaRespository playListMusicaRespository;

    @Autowired
    private UsuarioService usuarioService;

    public PlayListDTO converterParaPlaylistDTO (PlayList playList){
        return objectMapper.convertValue(playList,PlayListDTO.class);
    }

    public PlayList converterParaPlaylist (PlayListCreate playList){
        return objectMapper.convertValue(playList,PlayList.class);
    }

    public void validUser(Integer idUsuario) throws SQLException, PessoaNaoCadastradaException {
        UsuarioDTO usuarioDTO = usuarioService.findById(idUsuario);
        System.out.println(usuarioDTO);


    }

    public PlayListDTO create (PlayListCreate playlistCreate,
                               Integer idUsuario) throws SQLException, PessoaNaoCadastradaException {
        validUser(idUsuario);
        playlistCreate.setIdUsuario(idUsuario);
        PlayList playList = converterParaPlaylist(playlistCreate);
        playList = playListRepository.create(playList);

        if(playlistCreate.getMusicas() != null && !playlistCreate.getMusicas().isEmpty()){
            for(MusicaDTO music: playlistCreate.getMusicas()){
                playListMusicaRespository.create(playList.getIdPlaylist(),
                        music.getId());
            }
        }

        PlayListDTO playListDTO = converterParaPlaylistDTO(playList);
        playListDTO.setMusicas(playlistCreate.getMusicas());
        return playListDTO;
    }

//    public List<PlayListDTO> list (PlayListDTO playlistDTO) throws SQLException, PessoaNaoCadastradaException {
//        PlayList playList = converterParaPlaylist(playlistDTO);
//        Usuario usuario = null;
//        repository.list(usuario);
//        return (List<PlayListDTO>) playlistDTO;
//    }
//
//    public PlayListDTO update (PlayListDTO playlistAtualizar, Integer id) throws PessoaNaoCadastradaException, SQLException {
//        Usuario userRecuperado = null;
//        PlayList playListRecuperada = getPlaylistById(id);
//        playListRecuperada.setIdPlaylist(playlistAtualizar.getIdPlayList());
//        playListRecuperada.setNome(playlistAtualizar.getName());
//        return converterParaPlaylistDTO(playListRecuperada);
//    }
//
//    public PlayList getPlaylistById (Integer id) throws PessoaNaoCadastradaException, SQLException {
//        Usuario user = null;
//        PlayList playlistRecuperada = repository.list(user).stream()
//                .filter(playList -> playList.getIdPlaylist().equals(id))
//                .findFirst()
//                .orElseThrow(() -> new PessoaNaoCadastradaException("Playlist não econtrada"));
//        return playlistRecuperada;
//    }
//
//    public void delete (Integer id) throws PessoaNaoCadastradaException, SQLException {
//        Usuario user = null;
//        PlayList playlistRecuperada = getPlaylistById(id);
//        repository.list(user).remove(playlistRecuperada);
//    }
//
//    public PlayListDTO listByName (String nome) throws SQLException, PessoaNaoCadastradaException {
//        Usuario user = null;
//        return repository.list(user).stream()
//                .filter(playlist -> playlist.getNome().toUpperCase().contains(nome.toUpperCase()))
//                .map(this::converterParaPlaylistDTO)
//                .findFirst()
//                .orElseThrow(() -> new PessoaNaoCadastradaException("Playlist não econtrada"));
//    }

}
