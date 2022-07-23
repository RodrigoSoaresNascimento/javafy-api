package br.com.javafy.service;

import br.com.javafy.client.spotify.SpotifyAuthorization;
import br.com.javafy.client.spotify.SpotifyClient;
import br.com.javafy.dto.spotify.MusicaDTO;
import br.com.javafy.dto.spotify.MusicaFullDTO;
import br.com.javafy.dto.spotify.QueryDTO;
import br.com.javafy.dto.spotify.TokenDTO;
import br.com.javafy.entity.Headers;
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
    ObjectMapper objectMapper;

    @Autowired
    private Headers headers;

    private TokenDTO getToken(){
        return spotifyAutorization
                .authorization(headers.toDados(), headers.getGrantType());
    }

    private List<MusicaDTO> convertJsonToMusicaDTO(Map<String, Object> tracks)
            throws JsonProcessingException {
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

    public MusicaFullDTO musicById(String id) {
        TokenDTO tokenDTO = getToken();
        return spotifyClient.getTrack(tokenDTO.getAutorization(), id);
    }

    public List<MusicaFullDTO> getList() {
        TokenDTO tokenDTO = getToken();
        String ids = "7ouMYWpwJ422jRcDASZB7P,4VqPOruhp5EdPBeR92t6lQ,2takcwOaAZWiXQijPHIx7B";
        return spotifyClient.getTracks(tokenDTO.getAutorization(), ids).get("tracks");
    }

    public List<MusicaDTO> searchMusic(String query) throws JsonProcessingException {
        System.out.println("QUery " + query);
        TokenDTO tokenDTO = getToken();
        var t = spotifyClient.search(
                tokenDTO.getAutorization(),
                query.replace(" ", "+"),
                "track"
        ).get("tracks");
        return convertJsonToMusicaDTO(t);
    }

}

