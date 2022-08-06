package br.com.javafy.service;

import br.com.javafy.client.spotify.SpotifyAuthorization;
import br.com.javafy.client.spotify.SpotifyClient;
import br.com.javafy.dto.spotify.TokenDTO;
import br.com.javafy.dto.spotify.artista.ArtistaDTO;
import br.com.javafy.dto.spotify.artista.RootArtista;
import br.com.javafy.dto.spotify.artista.TrackArtista;
import br.com.javafy.dto.spotify.musica.MusicaFullDTO;
import br.com.javafy.entity.Headers;
import br.com.javafy.exceptions.PlaylistException;
import br.com.javafy.exceptions.SpotifyException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class ArtistaServiceTest {

    @InjectMocks
    private ArtistaService artistaService;

    @Mock
    private SpotifyClient spotifyClient;

    @Mock
    private SpotifyAuthorization spotifyAutorization;

    @Mock
    private Headers headers;

    @Before
    public void init() {
        TokenDTO tokenDTO =
                new TokenDTO("808b8b778", "Bearer ", 3600);
        when(spotifyAutorization.authorization(headers.toDados(), headers.getGrantType()))
                .thenReturn(tokenDTO);
    }

    @Test
    public void deveTestarBuscaPlaylistById() throws SpotifyException {
        ArtistaDTO artista = getArtistaDTO();
        when(spotifyClient.getArtist(anyMap(), anyString())).thenReturn(artista);
        ArtistaDTO artistaDTO = artistaService.artistById("0TnOYISbd1XYRBk9myaseg");
        assertNotNull(artistaDTO);
    }

    @Test(expected = SpotifyException.class)
    public void deveTestarmusicByIdComExcecao() throws SpotifyException {
        when(spotifyClient.getTrack(anyMap(), anyString()));
        artistaService.artistById("iaisi");
    }

    @Test
    public void deveSearchMusic() throws  SpotifyException {
        String query = "Marisa";
        RootArtista rootArtista = getListTrackArtista();

        when(spotifyClient.getArtistTopTracks(anyMap(), anyString()))
                .thenReturn(rootArtista);

        List<TrackArtista> artistaTrack =  artistaService.searchArtist(query);
        assertEquals(1, artistaTrack.size());

    }

    @Test
    public void deveGetList() throws SpotifyException {
        Map<String, List<ArtistaDTO>> artistasMAP = new HashMap<>();
        artistasMAP.put("artists", List.of(getArtistaDTO()));

        when(spotifyClient.getArtists(anyMap(), anyString()))
                .thenReturn(artistasMAP);

        List<ArtistaDTO> artista = artistaService.getList("ids,ids");

        assertEquals(1, artista.size());
    }

    private RootArtista getListTrackArtista(){
        TrackArtista track = new TrackArtista();
        track.setId("0TnOYISbd1XYRBk9myaseg");
        track.setName("Pitbull");
        track.setPopularity(100);
        track.setDurationMs(222520);
        track.setPreviewUrl("https://p.scdn.co/mp3-preview/faa5543826cbcfc6a14dd09ab8108bccbb13e8df?cid=68e29005ed5d4c838871f76bd274f6b8");
        ArtistaDTO artistaDTO = new ArtistaDTO();
        artistaDTO.setNome("Claudia Leitte");
        artistaDTO.setId("2OjoIDVPQKT9B7loZbPEfp");
        track.setArtistas(List.of(artistaDTO));

        RootArtista rootArtista = new RootArtista();
        rootArtista.setTracks(List.of(track));
        return rootArtista;
    }

    private ArtistaDTO getArtistaDTO() {
        ArtistaDTO artistaDTO = new ArtistaDTO();
        artistaDTO.setId("0TnOYISbd1XYRBk9myaseg");
        artistaDTO.setNome("Pitbull");
        return artistaDTO;
    }

}
