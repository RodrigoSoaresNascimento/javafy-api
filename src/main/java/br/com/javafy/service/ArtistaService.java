package br.com.javafy.service;


import br.com.javafy.client.spotify.SpotifyAuthorization;
import br.com.javafy.client.spotify.SpotifyClient;
import br.com.javafy.dto.spotify.ArtistaDTO;
import br.com.javafy.dto.spotify.MusicaDTO;
import br.com.javafy.dto.spotify.MusicaFullDTO;
import br.com.javafy.dto.spotify.TokenDTO;
import br.com.javafy.entity.Headers;
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
    ObjectMapper objectMapper;

    @Autowired
    private Headers headers;

    private TokenDTO getToken(){
        return spotifyAutorization
                .authorization(headers.toDados(), headers.getGrantType());
    }

    private List<ArtistaDTO> convertJsonToArtistaDTO(Map<String, Object> artists)
            throws JsonProcessingException {
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

    public ArtistaDTO artistById(String id) {
        TokenDTO tokenDTO = getToken();
        return spotifyClient.getArtist(tokenDTO.getAutorization(), id);
    }
    //todo -> remover os getList()

    public List<ArtistaDTO> getList() {
        TokenDTO tokenDTO = getToken();
        String ids = "2CIMQHirSU0MQqyYHq0eOx,57dN52uHvrHOxijzpIgu3E,1vCWHaC5f2uS3yhpwWbIA6";
        return spotifyClient.getArtists(tokenDTO.getAutorization(), ids).get("artists");
    }

    public List<MusicaDTO> searchArtist(String id, String pais) throws JsonProcessingException {
        TokenDTO tokenDTO = getToken();
       return spotifyClient.getArtistTopTracks(tokenDTO.getAutorization(), id, pais).get("artists");
    }

}
