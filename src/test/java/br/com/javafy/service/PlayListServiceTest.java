package br.com.javafy.service;

import br.com.javafy.dto.PageDTO;
import br.com.javafy.dto.playlist.PlayListCreate;
import br.com.javafy.dto.playlist.PlayListDTO;
import br.com.javafy.dto.playlist.PlaylistAddMusicaDTO;
import br.com.javafy.dto.spotify.musica.MusicaCreateDTO;
import br.com.javafy.dto.spotify.musica.MusicaFullDTO;
import br.com.javafy.entity.CargoEntity;
import br.com.javafy.entity.MusicaEntity;
import br.com.javafy.entity.PlayListEntity;
import br.com.javafy.entity.UsuarioEntity;
import br.com.javafy.enums.CargosEnum;
import br.com.javafy.enums.Roles;
import br.com.javafy.exceptions.PessoaException;
import br.com.javafy.exceptions.PlaylistException;
import br.com.javafy.exceptions.SpotifyException;
import br.com.javafy.repository.PlayListRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.util.ReflectionTestUtils;
import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.*;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PlayListServiceTest {

    @InjectMocks
    private PlayListService playListService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private MusicaService musicaService;

    @Mock
    private PlayListRepository playListRepository;

    @Mock
    private UsuarioService usuarioService;

    @Before
    public void init(){
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        ReflectionTestUtils.setField(playListService, "objectMapper", objectMapper);
    }

    @Test
    public void deveRetornaPlaylistEntityById() throws PlaylistException {
        // Setup
        Integer idPlaylist = 1;
        String nomePlaylist = "Só as melhores";
        UsuarioEntity usuario = getUsuarioPremium();
        PlayListEntity playListEntity =
                getPlayListEntity(idPlaylist, nomePlaylist, usuario);

        when(playListRepository.findById(any(Integer.class))).thenReturn(
                Optional.of(playListEntity)
        );

        // Ação
        PlayListEntity playList = playListService.retornaPlaylistEntityById(idPlaylist);

        // Asserts
        assertNotNull(playList);
        assertEquals(1, playList.getIdPlaylist().intValue());
        assertEquals(nomePlaylist, playList.getName());
        assertEquals(usuario, playList.getUsuario());
    }

    @Test(expected = PlaylistException.class)
    public void deveRetornaPlaylistEntityByIdException() throws PlaylistException {
        // Setup
        when(playListRepository.findById(any(Integer.class))).thenReturn(
                Optional.empty()
        );

        // Ação
        playListService.retornaPlaylistEntityById(10);
    }

    @Test
    public void deveGetPlaylistWithIdWithMusics()
            throws PlaylistException{
        // SETUP
        Integer idPlaylist = 1;
        String nomePlaylist = "Playlist boa demais";
        UsuarioEntity usuario = getUsuarioPremium();
        PlayListEntity playListEntity =
                getPlayListEntity(idPlaylist, nomePlaylist, usuario);

        when(playListRepository.findById(any(Integer.class))).thenReturn(
                Optional.of(playListEntity)
        );

        when(playListService.retornaPlaylistEntityById(anyInt())).thenReturn(
                playListEntity
        );

        // Ação
        //PlayListDTO playListDTO = playListService.getPlaylistWithIdWithMusics(idPlaylist);
    }

    @Test
    public void deveGetPlaylistWithIdWithNotMusics() throws PlaylistException {
        Integer idPlaylist = 1;
        UsuarioEntity usuario = getUsuarioPremium();
        String nomePlaylist = "Nome legal";
        PlayListEntity playListEntity = getPlayListEntity(idPlaylist, nomePlaylist, usuario);

        when(playListRepository.findById(any(Integer.class))).thenReturn(
                Optional.of(playListEntity)
        );

        // Act
        PlayListDTO playListDTO =
                playListService.getPlaylistWithIdWithNotMusics(idPlaylist);

        // Assert
        assertNotNull(playListDTO);
        assertEquals(nomePlaylist, playListDTO.getName());
        assertEquals(idPlaylist, playListDTO.getIdPlaylist());
        assertNull(playListDTO.getMusicas());
    }

    @Test
    public void deveTestarCreateComSucesso() throws PessoaException,
            PlaylistException, SpotifyException {
        // Setup
        String nomePlaylist = "Playlist Sofrência";
        Integer idPlaylist = 1;

        UsuarioEntity usuarioEntity = getUsuarioPremium();
        PlayListCreate playListCreate = new PlayListCreate(nomePlaylist);
        PlayListEntity playListEntity = getPlayListEntity(idPlaylist, nomePlaylist, usuarioEntity);

        when(usuarioService.retornarUsuarioEntityById()).thenReturn(getUsuarioPremium());
        when(playListRepository.save(any(PlayListEntity.class))).thenReturn(playListEntity);

        // Ação
        PlayListDTO playListDTO = playListService.create(playListCreate);

        // Assert
        assertNotNull(playListDTO);
        assertEquals(nomePlaylist, playListDTO.getName());
        assertNull(playListDTO.getMusicas());
        assertEquals(1, playListDTO.getIdPlaylist().intValue());
    }

    @Test
    public void deveFazerUpdateComSucesso() throws
            PlaylistException, SpotifyException, PessoaException {
        Integer idPlaylist = 1;
        UsuarioEntity usuario = getUsuarioPremium();

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(
                        usuario.getLogin(),
                        usuario.getSenha()
                );

        SecurityContextHolder.getContext()
                .setAuthentication(usernamePasswordAuthenticationToken);

        String nomePlaylist = "Nova Playlist";
        PlayListEntity playList = getPlayListEntity(idPlaylist,nomePlaylist, usuario);
        playList.setMusicas(new HashSet<>());
        playList.getMusicas().clear();

        PlaylistAddMusicaDTO playlistAddMusicaDTO = new PlaylistAddMusicaDTO();
        String novoNome = "Novo nome da playlist";
        playlistAddMusicaDTO.setName("Novo nome da playlist");
        String idMusica = "0csbIHcIB2Xu0QmfmulXul";
        playlistAddMusicaDTO.setMusicas(List.of(new MusicaCreateDTO(idMusica)));

        when(playListRepository.findById(any(Integer.class))).thenReturn(
                Optional.of(playList)
        );

        doNothing().when(musicaService).saveMusicaRepository(Set.of());

        when(playListRepository.save(any(PlayListEntity.class))).thenReturn(playList);
        when(usuarioService.retornarUsuarioEntityById()).thenReturn(usuario);


        // ACT

        PlayListDTO playListDTO =
                playListService.update(idPlaylist, playlistAddMusicaDTO);

        // Assert
        assertNotNull(playListDTO);
        assertEquals(novoNome, playListDTO.getName());

    }

    @Test(expected = PlaylistException.class)
    public void deveFazerUpdateSemSucesso() throws
            PlaylistException, SpotifyException, PessoaException {

        Integer idPlaylist = 1;
        UsuarioEntity usuario = getUsuarioPremium();
        String nomePlaylist = "Nova Playlist";

        PlaylistAddMusicaDTO playlistAddMusicaDTO = new PlaylistAddMusicaDTO();

        when(playListRepository.findById(any(Integer.class))).thenReturn(
                Optional.of(getPlayListEntity(idPlaylist,nomePlaylist, usuario))
        );

        UsuarioEntity outroUsuario = getUsuarioAdmin();
        when(usuarioService.retornarUsuarioEntityById()).thenReturn(outroUsuario);

        // ACT
        PlayListDTO playListDTO =
                playListService.update(idPlaylist, playlistAddMusicaDTO);

    }

    @Test
    public void deveTestarListPaginadoComSucesso() {
        List<PlayListEntity> playListEntities =
                List.of(getPlayListEntity(1, "DA HORA", getUsuarioPremium()));
        Page<PlayListEntity> pagePlaylist = new PageImpl<>(playListEntities);

        when(playListRepository.findAll(any(Pageable.class)))
                .thenReturn(pagePlaylist);
        Integer pagina = 0;
        Integer registros = 3;
        PageDTO<PlayListDTO> pageDTO = playListService.getListPlayList(pagina, registros);

        assertNotNull(pageDTO);
        assertEquals(pagina, pageDTO.getPage());
        assertEquals(registros, pageDTO.getSize());
        assertEquals(1, pageDTO.getContent().size());
    }

    @Test
    public void deveDeletarPlaylistComSucesso() throws PlaylistException, PessoaException {
        Integer idPlaylist = 1;
        String nome = "Playlist para deletar";
        UsuarioEntity usuario = getUsuarioPremium();

        PlayListEntity playListEntity = getPlayListEntity(idPlaylist, nome, usuario);

        when(playListRepository.findById(anyInt()))
                .thenReturn(Optional.of(playListEntity));

        when(usuarioService.retornarUsuarioEntityById()).thenReturn(usuario);

        doNothing().when(playListRepository).delete(any(PlayListEntity.class));

        // act
        playListService.delete(idPlaylist);
        verify(playListRepository, times(1)).delete(any(PlayListEntity.class));

    }

    @Test
    public void deveDeletarPlaylistWhereUserIsAdminComSucesso() throws PlaylistException, PessoaException {
        Integer idPlaylist = 1;
        String nome = "Playlist para deletar";
        UsuarioEntity usuario = getUsuarioPremium();

        PlayListEntity playListEntity = getPlayListEntity(idPlaylist, nome, usuario);

        when(playListRepository.findById(anyInt()))
                .thenReturn(Optional.of(playListEntity));

        UsuarioEntity usuarioAdmin = getUsuarioAdmin();
        when(usuarioService.retornarUsuarioEntityById()).thenReturn(usuarioAdmin);

        doNothing().when(playListRepository).delete(any(PlayListEntity.class));

        // act
        playListService.delete(idPlaylist);
        verify(playListRepository, times(1)).delete(any(PlayListEntity.class));

    }

    @Test(expected = PlaylistException.class)
    public void deveDeletarPlaylistSemSucesso() throws PlaylistException, PessoaException {
        Integer idPlaylist = 1;
        String nome = "Playlist para deletar";
        UsuarioEntity usuario = getUsuarioPremium();

        PlayListEntity playListEntity = getPlayListEntity(idPlaylist, nome, usuario);

        when(playListRepository.findById(anyInt()))
                .thenReturn(Optional.of(playListEntity));

        UsuarioEntity outroUsuario = getUsuarioPremium();
        outroUsuario.setIdUsuario(2);

        when(usuarioService.retornarUsuarioEntityById()).thenReturn(outroUsuario);

        // act
        playListService.delete(idPlaylist);

        verify(playListRepository, times(1)).delete(any(PlayListEntity.class));


    }

    @Test
    public void deveRemoverMusica() throws PessoaException {
        MusicaEntity musica = new MusicaEntity("0csbIHcIB2Xu0QmfmulXul", Set.of());

        Integer idPlaylist = 1;
        String nome = "Playlist para deletar";
        UsuarioEntity usuario = getUsuarioPremium();

        PlayListEntity playListEntity = getPlayListEntity(idPlaylist, nome, usuario);
        playListEntity.setMusicas(new HashSet<>());
        playListEntity.getMusicas().add(musica);

        when(playListRepository.findById(anyInt()))
                .thenReturn(Optional.of(playListEntity));

        when(usuarioService.retornarUsuarioEntityById()).thenReturn(usuario);

        doNothing().when(playListRepository).save(playListEntity);

        verify(playListRepository, times(1))
                .save(any(PlayListEntity.class));

    }

    private PlayListDTO getPlaylistDTOComMusica(PlayListEntity playList){
        PlayListDTO playListDTO = new PlayListDTO();
        playListDTO.setIdPlaylist(playList.getIdPlaylist());
        playListDTO.setName(playList.getName());
        List<MusicaFullDTO> musicas = new ArrayList<>();
        MusicaFullDTO musicaFullDTO = new MusicaFullDTO();
        musicaFullDTO.setIdMusica("0csbIHcIB2Xu0QmfmulXul");
        musicaFullDTO.setNome("Mulher Monumento / Vem Me Amar / Ou Rói ou Tem Paixão");
        musicaFullDTO.setPopularidade(23);
        musicas.add(musicaFullDTO);
        playListDTO.setMusicas(musicas);
        return playListDTO;

    }

    private PlayListEntity getPlayListEntity(Integer idPlaylist,
                                             String nomePlaylist,
                                             UsuarioEntity usuarioEntity) {
        PlayListEntity playListEntity = new PlayListEntity();
        playListEntity.setIdPlaylist(idPlaylist);
        playListEntity.setUsuario(usuarioEntity);
        playListEntity.setName(nomePlaylist);
        playListEntity.setMusicas(Set.of());
        return playListEntity;
    }

    private UsuarioEntity getUsuarioPremium() {
        UsuarioEntity usuarioEntity = new UsuarioEntity();
        usuarioEntity.setIdUsuario(1);
        usuarioEntity.setEnable(true);
        usuarioEntity.setCargo(new CargoEntity(2, CargosEnum.ofTipo(Roles.PREMIUM), Set.of()));
        usuarioEntity.setGenero("M");
        usuarioEntity.setNome("Cleber");
        usuarioEntity.setDataNascimento(LocalDate.of(1994, 10, 13));
        usuarioEntity.setEmail("faker@faker.com");
        usuarioEntity.setSenha("1234");
        usuarioEntity.setLogin("login");
        return usuarioEntity;
    }

    private UsuarioEntity getUsuarioAdmin() {
        UsuarioEntity usuarioEntity = new UsuarioEntity();
        usuarioEntity.setIdUsuario(2);
        usuarioEntity.setEnable(true);
        usuarioEntity.setCargo(new CargoEntity(3, CargosEnum.ofTipo(Roles.ADMIN), Set.of()));
        usuarioEntity.setGenero("M");
        usuarioEntity.setNome("Cleber");
        usuarioEntity.setDataNascimento(LocalDate.of(1994, 10, 13));
        usuarioEntity.setEmail("faker@faker.com");
        usuarioEntity.setSenha("1234");
        usuarioEntity.setLogin("login");
        return usuarioEntity;
    }

    private PlayListDTO getPlaylistDTO(){
        return new PlayListDTO(1, "nomeDoido", Arrays.asList());
    }

}
