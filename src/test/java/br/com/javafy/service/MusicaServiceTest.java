package br.com.javafy.service;

import br.com.javafy.client.spotify.SpotifyAuthorization;
import br.com.javafy.client.spotify.SpotifyClient;
import br.com.javafy.dto.spotify.TokenDTO;
import br.com.javafy.dto.spotify.artista.ArtistaDTO;
import br.com.javafy.dto.spotify.musica.MusicaDTO;
import br.com.javafy.dto.spotify.musica.MusicaFullDTO;
import br.com.javafy.dto.spotify.musica.TracksDTO;
import br.com.javafy.dto.spotify.musica.TracksROOT;
import br.com.javafy.entity.Headers;
import br.com.javafy.exceptions.PlaylistException;
import br.com.javafy.repository.MusicaRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class MusicaServiceTest {

    @InjectMocks
    private MusicaService musicaService;

    @Mock
    private SpotifyClient spotifyClient;

    @Mock
    private SpotifyAuthorization spotifyAutorization;

    @Mock
    private MusicaRepository musicaRepository;

    @Mock
    private Headers headers;

    @Before
    public void init(){
        TokenDTO tokenDTO = new TokenDTO("808b8b778", "Bearer ", 3600);
        when(spotifyAutorization.authorization(headers.toDados(), headers.getGrantType()))
                .thenReturn(tokenDTO);
    }

    @Test
    public void deveTestarmusicById() throws PlaylistException {
        MusicaFullDTO musicaFullDTO = getMusicaFullDTO();
        when(spotifyClient.getTrack(anyMap(), anyString())).thenReturn(musicaFullDTO);
        MusicaFullDTO musica = musicaService.musicById("iaisi");
        assertNotNull(musica);
    }

    @Test
    public void deveGetList() throws PlaylistException {
        Map<String, List<MusicaFullDTO>> musicasMAP = new HashMap<>();
        musicasMAP.put("tracks", List.of(getMusicaFullDTO()));

        when(spotifyClient.getTracks(anyMap(), anyString()))
                .thenReturn(musicasMAP);
        List<MusicaFullDTO> musicas = musicaService.getList("ids,ids");
        assertNotNull(musicas);
        assertEquals(1, musicas.size());
    }

    @Test(expected = PlaylistException.class)
    public void deveTestarmusicByIdComExcecao() throws PlaylistException {
        when(spotifyClient.getTrack(anyMap(), anyString()));
         musicaService.musicById("iaisi");
    }

    @Test
    public void deveSearchMusic() throws PlaylistException {
        String query = "Marisa";
        when(spotifyClient.search(anyMap(), anyString(), anyString())).thenReturn(getTracksRoot());
        List<MusicaDTO> musicas = musicaService.searchMusic(query);
        assertNotNull(musicas);
        assertEquals(1, musicas.size());
        assertEquals("2FU0FRwKmvTZd0lADxXJs7", musicas.get(0).getIdMusica());

    }

    @Test
    public void deveSalvarMusicaNaPlaylist(){

        musicaService.saveMusicaRepository(anySet());

        verify(musicaRepository, times(1))
                .saveAll(anySet());
    }

    private MusicaFullDTO getMusicaFullDTO(){
        MusicaFullDTO musicaFullDTO = new MusicaFullDTO();
        musicaFullDTO.setIdMusica("2FU0FRwKmvTZd0lADxXJs7");
        musicaFullDTO.setNome("Harp Concerto in B flat, Op.4, No.6, HWV 294: 1. Andante allegro");
        musicaFullDTO.setPopularidade(0);
        musicaFullDTO.setDuracaoMs(146466);

        ArtistaDTO artistaDTO = new ArtistaDTO();
        artistaDTO.setId("1QL7yTHrdahRMpvNtn6rI2");
        artistaDTO.setNome("George Frideric Handel");
        musicaFullDTO.setArtistas(List.of(artistaDTO));
        return musicaFullDTO;
        }

    private TracksROOT getTracksRoot(){
            MusicaFullDTO musicaFullDTO = getMusicaFullDTO();
            TracksROOT tracksROOT = new TracksROOT();

            TracksDTO  tracksDTO = new TracksDTO();
            tracksDTO.setMusicas(Arrays.asList(musicaFullDTO));
            tracksROOT.setTracks(tracksDTO);
            return tracksROOT;
        }
}
