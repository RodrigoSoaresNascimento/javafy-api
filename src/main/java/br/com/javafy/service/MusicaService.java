package br.com.javafy.service;

import br.com.javafy.entity.Musica;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MusicaService {
    private final SpotifyClient spotifyClient;

    List<Musica> musicas = new ArrayList<>();

//    ************* criar os metodos de conversao de Musica para MusicaDTO e o contrario

    // Retorna uma musica, especifica
    public Musica getMusicaPorId(String id) {
//        return spotifyClient.getTrack(id);
    }

    // Retorna uma lista de musicas, filtrando por nome ou trecho
    public List<Musica> getMusicaPorNome(String nome) {
        return null;
    }

    // Busca uma musica por id
    public List<Musica> getmusicasPorArtista(String artista) {
        //tbm fazer essa busca direto no controller, pq recebe um @RequestParam("nome")
        return null;
    }
}

