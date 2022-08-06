package br.com.javafy.service;


import br.com.javafy.client.spotify.SpotifyAuthorization;
import br.com.javafy.client.spotify.SpotifyClient;
import br.com.javafy.dto.spotify.artista.ArtistaDTO;
import br.com.javafy.dto.spotify.TokenDTO;
import br.com.javafy.dto.spotify.artista.TracksRoot;
import br.com.javafy.dto.spotify.musica.MusicaDTO;
import br.com.javafy.dto.spotify.musica.TracksROOT;
import br.com.javafy.entity.Headers;
import br.com.javafy.exceptions.PlaylistException;
import br.com.javafy.exceptions.SpotifyException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ArtistaService {

    @Autowired
    private SpotifyClient spotifyClient;

    @Autowired
    private SpotifyAuthorization spotifyAutorization;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private Headers headers;

    private TokenDTO getToken() throws SpotifyException {
        try {
            return spotifyAutorization
                    .authorization(headers.toDados(), headers.getGrantType());
        } catch (Exception e){
            throw new SpotifyException("Erro na autenticação do spotify");
        }
    }

    private List<ArtistaDTO> convertJsonToArtistaDTO(Map<String, Object> artists){
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        List<Map<String, Object>> items = (List<Map<String, Object>>) artists.get("items");
        return items.stream().
                map(stringObjectMap -> {
                    String jsonResult = null;
                    try {
                        jsonResult = mapper.writerWithDefaultPrettyPrinter()
                                .writeValueAsString(stringObjectMap);
                        return mapper.readValue(jsonResult, ArtistaDTO.class);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                    return null;
                }).toList();
    }

    public ArtistaDTO artistById(String id) throws SpotifyException {
        return spotifyClient.getArtist(getToken().getAutorization(), id);
    }

    public List<MusicaDTO> searchArtist(String id, String pais) throws SpotifyException {
        //TokenDTO tokenDTO = getToken();
        //TracksRoot tracks = spotifyClient.getArtistTopTracks(tokenDTO.getAutorization(), id);
        return null;
    }

    public List<ArtistaDTO> getList(String ids) throws PlaylistException, SpotifyException {
        return spotifyClient.getArtists(getToken().getAutorization(), ids).get("artists");
    }

}
