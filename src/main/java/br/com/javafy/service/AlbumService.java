package br.com.javafy.service;

import br.com.javafy.client.spotify.SpotifyAuthorization;
import br.com.javafy.client.spotify.SpotifyClient;
import br.com.javafy.dto.albums.AlbumDTO;
import br.com.javafy.dto.spotify.TokenDTO;
import br.com.javafy.entity.Headers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AlbumService {

    private final SpotifyAuthorization spotifyAuthorization;

    private final SpotifyClient spotifyClient;

    private final Headers headers;

    public AlbumDTO getAlbum(){
        TokenDTO authorization = spotifyAuthorization.authorization(headers.toDados(), headers.getGrantType());
        AlbumDTO album = spotifyClient.getAlbums(authorization.getAutorization(), "2up3OPMp9Tb4dAKM2erWXQ");
        return album;
    }
}
