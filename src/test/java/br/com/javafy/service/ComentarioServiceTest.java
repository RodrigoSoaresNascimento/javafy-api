package br.com.javafy.service;


import br.com.javafy.dto.PageDTO;
import br.com.javafy.dto.comentario.ComentarioCreateDTO;
import br.com.javafy.dto.comentario.ComentarioDTO;
import br.com.javafy.dto.comentario.ComentarioPlaylistRelatorioDTO;
import br.com.javafy.entity.CargoEntity;
import br.com.javafy.entity.ComentarioEntity;
import br.com.javafy.entity.PlayListEntity;
import br.com.javafy.entity.UsuarioEntity;
import br.com.javafy.enums.CargosEnum;
import br.com.javafy.enums.Roles;
import br.com.javafy.exceptions.ComentarioNaoCadastradoException;
import br.com.javafy.exceptions.PessoaException;
import br.com.javafy.exceptions.PlaylistException;
import br.com.javafy.repository.ComentariosRepository;
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
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ComentarioServiceTest {

    @InjectMocks // injetar a classe na qual é o foco do caso de uso...
    private ComentarioService comentarioService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private ComentariosRepository comentariosRepository;

    @Mock
    private PlayListService playListService;

    @Mock
    private UsuarioService usuarioService;


    @Before
    public void init(){
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        ReflectionTestUtils.setField(comentarioService, "objectMapper", objectMapper);
    }


    @Test
    public void deveTestarListComSucesso() throws ComentarioNaoCadastradoException {

        // setup

        List<ComentarioEntity> comentarios = new ArrayList<>();
        comentarios.add(getComentario());

        // act
        when(comentariosRepository.findAll()).thenReturn(comentarios);

        // assert
        List<ComentarioDTO> comentarioDTOS = comentarioService.list();

        assertNotNull(comentarioDTOS);
        assertTrue(!comentarioDTOS.isEmpty());

    }

    @Test
    public void deveTestarCriarComentarioComSucesso() throws ComentarioNaoCadastradoException, PessoaException, PlaylistException {

        // setup
        UsuarioEntity usuario = getUsuarioEntity();
        ComentarioEntity comentario = getComentario();

        // act
        when(usuarioService.retornarUsuarioEntityById()).thenReturn(usuario);
        when(comentariosRepository.save(any(ComentarioEntity.class))).thenReturn(comentario);

        // assert
        ComentarioDTO comentarioDTO = comentarioService.create(getComentario().getIdComentario(), getComentarioCreateDTO());

        assertNotNull(comentarioDTO);
        assertEquals(comentario.getComentario(), comentarioDTO.getComentario());

    }

    @Test
    public void deveTestarUpdateComentarioComSucesso() throws ComentarioNaoCadastradoException, PessoaException {

        // setup
        UsuarioEntity usuario = getUsuarioEntity();
        ComentarioEntity comentario = getComentario();

        // act
        when(usuarioService.retornarUsuarioEntityById()).thenReturn(usuario);
        when(comentariosRepository.findById(anyInt())).thenReturn(Optional.of(comentario));
        when(comentariosRepository.save(any(ComentarioEntity.class))).thenReturn(comentario);

        // assert
        ComentarioDTO comentarioDTO = comentarioService.update(getComentario().getIdComentario(), getComentarioCreateDTO());

        assertNotNull(comentarioDTO);
        assertEquals(comentario.getIdComentario(), comentarioDTO.getIdComentario());
        assertEquals(comentario.getComentario(), comentarioDTO.getComentario());

    }

    @Test(expected = ComentarioNaoCadastradoException.class)
    public void deveTestarExcecaoComSucesso () throws ComentarioNaoCadastradoException, PessoaException {

        ComentarioEntity comentario = getComentario();

        when(comentariosRepository.findById(anyInt())).thenReturn(Optional.empty());

        comentarioService.update(comentario.getIdComentario(), getComentarioDTO());

    }

    @Test
    public void deveTestarDeleteComentarioComSucesso() throws ComentarioNaoCadastradoException, PessoaException {

        // setup

        UsuarioEntity usuario = getUsuarioEntity();
        Optional<ComentarioEntity> comentario = Optional.of(getComentario());

        // act
        when(usuarioService.retornarUsuarioEntityById()).thenReturn(usuario);
        when(comentariosRepository.findById(anyInt())).thenReturn(comentario);
        doNothing().when(comentariosRepository).delete(any(ComentarioEntity.class));

        // assert
        comentarioService.delete(10);

        verify(comentariosRepository, times(1)).delete(any(ComentarioEntity.class));

    }

    @Test
    public void deveTestarComentarioPaginadoComSucesso() {
        // setup
        List<ComentarioEntity> cometarios = List.of(getComentario());
        Page<ComentarioEntity> pageComentarios = new PageImpl<>(cometarios);

        when(comentariosRepository.findAll(any(Pageable.class)))
                .thenReturn(pageComentarios);

        // act
        PageDTO<ComentarioDTO> paginaDeComentarios = comentarioService
                .listarComentariosPaginado(1000, 3);

        // assert
        assertNotNull(paginaDeComentarios);
        assertEquals(1, pageComentarios.getTotalPages());
        assertEquals(1, paginaDeComentarios.getContent().size());
    }

    @Test
    public void deveDeletarComentarioWhereUserIsAdminComSucesso() throws PessoaException, ComentarioNaoCadastradoException {

        UsuarioEntity usuario = getUsuarioEntity();

        ComentarioEntity comentarioEntity = getComentario();

        when(comentariosRepository.findById(anyInt()))
                .thenReturn(Optional.of(comentarioEntity));

        UsuarioEntity usuarioAdmin = getUsuarioAdmin();
        when(usuarioService.retornarUsuarioEntityById()).thenReturn(usuarioAdmin);

        doNothing().when(comentariosRepository).delete(any(ComentarioEntity.class));

        // act
        comentarioService.delete(comentarioEntity.getIdComentario());
        verify(comentariosRepository, times(1)).delete(any(ComentarioEntity.class));

    }

    @Test(expected = ComentarioNaoCadastradoException.class)
    public void nãoDeveDeletarComentarioWhereUserIsNotAdmin() throws PessoaException, ComentarioNaoCadastradoException {

        ComentarioEntity comentarioEntity = getComentario();

        when(comentariosRepository.findById(anyInt()))
                .thenReturn(Optional.of(comentarioEntity));

        UsuarioEntity usuarioPremium = getUsuarioFree();
        when(usuarioService.retornarUsuarioEntityById()).thenReturn(usuarioPremium);

        // act
        comentarioService.delete(comentarioEntity.getIdComentario());

    }

    @Test
    public void deveBuscarComentarioPeloDTOId () throws ComentarioNaoCadastradoException {
        // setup

        Optional<ComentarioEntity> comentario = Optional.of(getComentario());

        when(comentariosRepository.findById(anyInt())).thenReturn(comentario);

        // act
        ComentarioDTO comentarioDTO = comentarioService.findComentarioDTOById(comentario.get().getIdComentario());

        // assert
        assertNotNull(comentarioDTO);
        assertEquals(comentario.get().getIdComentario(), comentarioDTO.getIdComentario());
        assertEquals(comentario.get().getComentario(), comentarioDTO.getComentario());

    }

    @Test
    public void deveTestarRelatorioComentarioPlayListComSucesso () {

        ComentarioPlaylistRelatorioDTO relatorioDTO = new ComentarioPlaylistRelatorioDTO();
        relatorioDTO.setComentario(getComentario().getComentario());
        relatorioDTO.setEmail(getUsuarioAdmin().getEmail());
        relatorioDTO.setNomeUsuario(getUsuarioEntity().getNome());
        relatorioDTO.setNomePlaylist(getPlayList().getName());

        List<ComentarioPlaylistRelatorioDTO> relatorioDTOS = List.of(relatorioDTO);

        when(comentariosRepository.relatorioComentarios()).thenReturn(relatorioDTOS);

        List<ComentarioPlaylistRelatorioDTO> relatorioUsuario = comentarioService.relatorioComentarioPlaylist();

        assertNotNull(relatorioUsuario);
        assertFalse(relatorioUsuario.isEmpty());
    }

    private static UsuarioEntity getUsuarioEntity() {
        UsuarioEntity usuarioEntity = new UsuarioEntity();
        usuarioEntity.setDataNascimento(LocalDate.of(1991, 9, 8));
        usuarioEntity.setEmail("maicon@teste.com.br");
        usuarioEntity.setNome("Maicon");
        usuarioEntity.setCargo(new CargoEntity(2, CargosEnum.ofTipo(Roles.PREMIUM), Set.of()));
        usuarioEntity.setIdUsuario(10);
        usuarioEntity.setGenero("M");
        usuarioEntity.setSenha("123");
        usuarioEntity.setLogin("user1");
        usuarioEntity.setEnable(true);
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

    private UsuarioEntity getUsuarioFree() {
        UsuarioEntity usuarioEntity = new UsuarioEntity();
        usuarioEntity.setIdUsuario(3);
        usuarioEntity.setEnable(true);
        usuarioEntity.setCargo(new CargoEntity(4, CargosEnum.ofTipo(Roles.FREE), Set.of()));
        usuarioEntity.setGenero("M");
        usuarioEntity.setNome("Rodrigo");
        usuarioEntity.setDataNascimento(LocalDate.of(1994, 10, 13));
        usuarioEntity.setEmail("faker@faker.com");
        usuarioEntity.setSenha("12345");
        usuarioEntity.setLogin("login12");
        return usuarioEntity;
    }


    private static ComentarioEntity getComentario() {
        ComentarioEntity comentario = new ComentarioEntity();
        comentario.setComentario("Musicas de forró");
        comentario.setIdComentario(10);
        comentario.setPlayList(getPlayList());
        comentario.setUsuarioEntity(getUsuarioEntity());
        return comentario;

    }

    private static ComentarioDTO getComentarioDTO() {
        ComentarioDTO comentario = new ComentarioDTO();
        comentario.setComentario(getComentarioCreateDTO().getComentario());
        comentario.setIdComentario(10);
        return comentario;

    }

    private static ComentarioCreateDTO getComentarioCreateDTO() {
        ComentarioCreateDTO comentario = new ComentarioCreateDTO();
        comentario.setComentario("Musicas de forró");
        return comentario;

    }

    private static PlayListEntity getPlayList (){
        PlayListEntity playList = new PlayListEntity();
        playList.setName("Minhas musicas");
        playList.setIdPlaylist(10);
        return playList;

    }

}
