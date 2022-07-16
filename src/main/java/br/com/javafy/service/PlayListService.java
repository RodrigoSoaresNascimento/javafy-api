package br.com.javafy.service;

import br.com.javafy.dto.PlayListDTO;
import br.com.javafy.entity.PlayList;
import br.com.javafy.entity.Usuario;
import br.com.javafy.exceptions.PessoaNaoCadastradaException;
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
    private PlayListRepository repository;

    public PlayListDTO converterParaPlaylistDTO (PlayList playList){
        return objectMapper.convertValue(playList,PlayListDTO.class);
    }

    public PlayList converterParaPlaylist (PlayListDTO playList){
        return objectMapper.convertValue(playList,PlayList.class);
    }

    public PlayListDTO create (PlayListDTO playListDTO) throws SQLException {

        repository.create(converterParaPlaylist(playListDTO));
        return playListDTO;
    }

    public List<PlayListDTO> list (PlayListDTO playlistDTO) throws SQLException, PessoaNaoCadastradaException {
        PlayList playList = converterParaPlaylist(playlistDTO);
        Usuario usuario = null;
        repository.list(usuario);
        return (List<PlayListDTO>) playlistDTO;

    }

    public PlayListDTO update (PlayListDTO playlistAtualizar, Integer id) throws PessoaNaoCadastradaException, SQLException {
        Usuario userRecuperado = null;
        PlayList playListRecuperada = getPlaylistById(id);
        playListRecuperada.setIdPlaylist(playlistAtualizar.getId());
        playListRecuperada.setNome(playlistAtualizar.getName());
        return converterParaPlaylistDTO(playListRecuperada);
    }

    public PlayList getPlaylistById (Integer id) throws PessoaNaoCadastradaException, SQLException {
        Usuario user = null;
        PlayList playlistRecuperada = repository.list(user).stream()
                .filter(playList -> playList.getIdPlaylist().equals(id))
                .findFirst()
                .orElseThrow(() -> new PessoaNaoCadastradaException("Playlist não econtrada"));
        return playlistRecuperada;
    }




    public void delete (Integer id) throws PessoaNaoCadastradaException, SQLException {
        Usuario user = null;
        PlayList playlistRecuperada = getPlaylistById(id);
        repository.list(user).remove(playlistRecuperada);
    }

    public PlayListDTO listByName (String nome) throws SQLException, PessoaNaoCadastradaException {
        Usuario user = null;
        return repository.list(user).stream()
                .filter(playlist -> playlist.getNome().toUpperCase().contains(nome.toUpperCase()))
                .map(this::converterParaPlaylistDTO)
                .findFirst()
                .orElseThrow(() -> new PessoaNaoCadastradaException("Playlist não econtrada"));
    }

}
