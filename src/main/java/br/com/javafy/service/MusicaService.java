package br.com.javafy.service;

import br.com.javafy.client.spotify.SpotifyAuthorization;
import br.com.javafy.client.spotify.SpotifyClient;
import br.com.javafy.dto.spotify.TokenDTO;
import br.com.javafy.dto.spotify.genero.GeneroDTO;
import br.com.javafy.dto.spotify.musica.MusicaDTO;
import br.com.javafy.dto.spotify.musica.MusicaFullDTO;
import br.com.javafy.entity.Headers;
import br.com.javafy.entity.MusicaEntity;
import br.com.javafy.exceptions.PlaylistException;
import br.com.javafy.repository.MusicaRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MusicaService {

    @Autowired
    private SpotifyClient spotifyClient;

    @Autowired
    private SpotifyAuthorization spotifyAutorization;

    @Autowired
    private MusicaRepository musicaRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private Headers headers;

    private TokenDTO getToken() throws PlaylistException {
        try {
            return spotifyAutorization
                    .authorization(headers.toDados(), headers.getGrantType());
        } catch (Exception e){
            throw new PlaylistException("Erro na autenticação do spotify");
        }
    }

    private List<MusicaDTO> convertJsonToMusicaDTO(Map<String, Object> tracks){
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        List<Map<String, Object>> items = (List<Map<String, Object>>) tracks.get("items");
        return items.stream().
                map(stringObjectMap -> {
                    String jsonResult = null;
                    try {
                        jsonResult = mapper.writerWithDefaultPrettyPrinter()
                                .writeValueAsString(stringObjectMap);
                        return mapper.readValue(jsonResult, MusicaDTO.class);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                    return null;
                }).toList();
    }

    public MusicaFullDTO musicById(String id) throws PlaylistException {
        return spotifyClient.getTrack(getToken().getAutorization(), id);
    }

    public List<MusicaFullDTO> getList(String ids) throws PlaylistException {
        return spotifyClient.getTracks(getToken().getAutorization(), ids).get("tracks");
    }

    public List<MusicaDTO> searchMusic(String query) throws PlaylistException {
        var t = spotifyClient.search(
                getToken().getAutorization(),
                query.replace(" ", "+"),
                "track"
        ).get("tracks");
        return convertJsonToMusicaDTO(t);
    }

    public void saveMusicaRepository(Set<MusicaEntity> musicaEntities) throws PlaylistException {
        try {
            musicaRepository.saveAll(musicaEntities);
        } catch (Exception e){
            throw new PlaylistException("Error ao salvar a musica");
        }
    }

    public GeneroDTO listarGenero() throws PlaylistException {
        return spotifyClient.getGenre(getToken().getAutorization());
    }

}

