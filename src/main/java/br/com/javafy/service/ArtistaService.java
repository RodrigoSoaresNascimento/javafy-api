package br.com.javafy.service;


import br.com.javafy.client.spotify.SpotifyAuthorization;
import br.com.javafy.client.spotify.SpotifyClient;
import br.com.javafy.dto.spotify.artista.ArtistaDTO;
import br.com.javafy.dto.spotify.TokenDTO;
import br.com.javafy.dto.spotify.artista.RootArtista;
import br.com.javafy.dto.spotify.artista.TrackArtista;
import br.com.javafy.entity.Headers;
import br.com.javafy.exceptions.SpotifyException;
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
    private Headers headers;

    private TokenDTO getToken() throws SpotifyException {
        try {
            return spotifyAutorization
                    .authorization(headers.toDados(), headers.getGrantType());
        } catch (Exception e){
            throw new SpotifyException("Erro na autenticação do spotify");
        }
    }

    public ArtistaDTO artistById(String id) throws SpotifyException {
        return spotifyClient.getArtist(getToken().getAutorization(), id);
    }

    public List<TrackArtista> searchArtist(String id) throws SpotifyException {
        TokenDTO tokenDTO = getToken();
        RootArtista tracks = spotifyClient.getArtistTopTracks(tokenDTO.getAutorization(), id);
        return tracks.getTracks();
    }

    public List<ArtistaDTO> getList(String ids) throws SpotifyException {
        Map<String, List<ArtistaDTO>> artistasMap =
                spotifyClient.getArtists(getToken().getAutorization(), ids);
        return artistasMap.get("artists");
    }

}
