package br.com.javafy.service;

import br.com.javafy.client.spotify.SpotifyAuthorization;
import br.com.javafy.client.spotify.SpotifyClient;
import br.com.javafy.dto.spotify.TokenDTO;
import br.com.javafy.dto.spotify.musica.MusicaDTO;
import br.com.javafy.dto.spotify.musica.MusicaFullDTO;
import br.com.javafy.entity.Headers;
import br.com.javafy.enums.CargosUser;
import br.com.javafy.enums.Roles;
import br.com.javafy.exceptions.PlaylistException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.Before;
import org.junit.Test;
import org.junit.function.ThrowingRunnable;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.BDDAssertions.then;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class MusicaServiceTest {

    @InjectMocks
    private MusicaService musicaService;

    //private ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private SpotifyClient spotifyClient;

    @Mock
    private SpotifyAuthorization spotifyAutorization;

    @Mock
    private Headers headers;

//    @Before
//    public void init(){
//        objectMapper.registerModule(new JavaTimeModule());
//        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
//        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
//        ReflectionTestUtils.setField(musicaService, "objectMapper", objectMapper);
//    }

    @Test
    public void deveTestarmusicById() throws PlaylistException {
        TokenDTO tokenDTO = new TokenDTO("808b8b778", "Bearer ", 3600);

        MusicaFullDTO musicaFullDTO = new MusicaFullDTO();
        musicaFullDTO.setIdMusica("12698127");
        musicaFullDTO.setNome("Depois");
        musicaFullDTO.setPopularidade(74);
        musicaFullDTO.setDuracaoMs(3600);


        when(spotifyAutorization.authorization(headers.toDados(), headers.getGrantType()))
                .thenReturn(tokenDTO);

        when(spotifyClient.getTrack(anyMap(), anyString())).thenReturn(musicaFullDTO);

        MusicaFullDTO musica = musicaService.musicById("iaisi");

        assertNotNull(musica);
        assertEquals("Depois", musica.getNome());
    }

    @Test(expected = PlaylistException.class)
    public void deveTestarmusicByIdComExcecao() throws PlaylistException {
        when(spotifyClient.getTrack(anyMap(), anyString()));
         musicaService.musicById("iaisi");
    }

    @Test
    public void deveSearchMusic() throws PlaylistException {
        Map<String, Object> tracks = new HashMap<>();
        String busca = "musica";
        TokenDTO tokenDTO = new TokenDTO("119rh2pork", "Bearer", 3600);


        //musicaService.searchMusic(anyString());
    }




}
