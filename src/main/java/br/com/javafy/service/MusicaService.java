package br.com.javafy.service;

import br.com.javafy.client.SpotifyClient;
import br.com.javafy.dto.spotify.TokenDTO;
import br.com.javafy.entity.Musica;
import br.com.javafy.entity.spotify.Headers;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MusicaService {

    @Autowired
    private final SpotifyClient spotifyClient;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private Headers headers;


    private TokenDTO getToken(){
        System.out.println("AQUI");
        return spotifyClient.autorization(headers.toDados(), headers.getGrantType());
    }


    List<Musica> musicas = new ArrayList<>();

//    ************* criar os metodos de conversao de Musica para MusicaDTO e o contrario

    // Retorna uma musica, especifica
    public Musica getMusicaPorId(String id) {
        TokenDTO tokenDTO = getToken();
        System.out.println(tokenDTO.getAccessToken());
        Map<String, String> h = new HashMap<>(Map.of("Authorization", autorization));
        h.put("Content-Type", "application/json");
        System.out.println(h);
        //potifyClient.getMusica(h, id);

        return null;
    }

    // Retorna uma lista de musicas, filtrando por nome ou trecho
    public List<Musica> getMusicaPorNome(String nome) {
        TokenDTO tokenDTO = getToken();
        return null;
    }

    // Busca uma musica por id
    public List<Musica> getmusicasPorArtista(String artista) {
        TokenDTO tokenDTO = getToken();
        //tbm fazer essa busca direto no controller, pq recebe um @RequestParam("nome")
        return null;
    }
}

