package br.com.javafy.service;

import br.com.javafy.client.spotify.SpotifyAuthorization;
import br.com.javafy.client.spotify.SpotifyClient;
import br.com.javafy.service.dto.spotify.MusicaDTO;
import br.com.javafy.service.dto.spotify.TokenDTO;
import br.com.javafy.entity.Musica;
import br.com.javafy.entity.spotify.Headers;
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

    public MusicaDTO musicById(String id) {
        TokenDTO tokenDTO = getToken();
        return spotifyClient.getTrack(tokenDTO.getAutorization(), id);
    }

    public List<MusicaDTO> getList() {
        TokenDTO tokenDTO = getToken();
        System.out.println(tokenDTO.getAutorization());

        String ids = "7ouMYWpwJ422jRcDASZB7P,4VqPOruhp5EdPBeR92t6lQ,2takcwOaAZWiXQijPHIx7B";
        return spotifyClient.getTracks(tokenDTO.getAutorization(), ids).get("tracks");
    }

    public Map searchMusic() {
        TokenDTO tokenDTO = getToken();

        var t = spotifyClient.seach(
                tokenDTO.getAutorization(),
                "Marisa + Monte",
                "track"
        );
        System.out.println(t);
        return t;
    }

    // Busca uma musica por id
    public List<Musica> getmusicasPorArtista(String artista) {
        TokenDTO tokenDTO = getToken();
        //tbm fazer essa busca direto no controller, pq recebe um @RequestParam("nome")
        return null;
    }
}

