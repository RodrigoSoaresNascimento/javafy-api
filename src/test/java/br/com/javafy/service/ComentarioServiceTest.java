package br.com.javafy.service;


import br.com.javafy.dto.comentario.ComentarioCreateDTO;
import br.com.javafy.dto.comentario.ComentarioDTO;
import br.com.javafy.dto.playlist.PlayListDTO;
import br.com.javafy.dto.usuario.UsuarioDTO;
import br.com.javafy.entity.CargoEntity;
import br.com.javafy.entity.ComentarioEntity;
import br.com.javafy.entity.PlayListEntity;
import br.com.javafy.entity.UsuarioEntity;
import br.com.javafy.enums.CargosEnum;
import br.com.javafy.enums.Roles;
import br.com.javafy.exceptions.ComentarioNaoCadastradoException;
import br.com.javafy.exceptions.PessoaException;
import br.com.javafy.repository.ComentariosRepository;
import br.com.javafy.repository.UsuarioRepository;
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

    @Mock
    private UsuarioRepository usuarioRepository;


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
    public void deveTestarCriarComentarioComSucesso() throws ComentarioNaoCadastradoException {

        // setup

        ComentarioEntity comentario = getComentario();

        // act

        when(comentariosRepository.save(any(ComentarioEntity.class))).thenReturn(comentario);

        // assert
        ComentarioDTO comentarioDTO = comentarioService.create(getComentario().getIdComentario(), getComentarioCreateDTO());

        assertNotNull(comentarioDTO);
        assertEquals(comentario.getIdComentario(), comentarioDTO.getIdComentario());
        assertEquals(comentario.getComentario(), comentarioDTO.getComentario());

    }


    @Test
    public void deveTestarUpdateComentarioComSucesso() throws ComentarioNaoCadastradoException, PessoaException {

        // setup

        ComentarioEntity comentario = getComentario();

        // act

        when(comentariosRepository.save(any(ComentarioEntity.class))).thenReturn(comentario);

        // assert
        ComentarioDTO comentarioDTO = comentarioService.update(getComentario().getIdComentario(), getComentarioCreateDTO());

        assertNotNull(comentarioDTO);
        assertEquals(comentario.getIdComentario(), comentarioDTO.getIdComentario());
        assertEquals(comentario.getComentario(), comentarioDTO.getComentario());

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
    public void deveTestarGetByIdComSucesso() throws PessoaException {
        Optional<UsuarioEntity> clienteEntityOptional = Optional.of(getUsuarioEntity());

        when(usuarioRepository.findById(anyInt())).thenReturn(clienteEntityOptional);

        UsuarioDTO clienteDTO = usuarioService.findById();

        assertNotNull(clienteDTO);
        assertEquals(clienteEntityOptional.get().getIdUsuario(), clienteDTO.getIdUsuario());
        assertEquals(clienteEntityOptional.get().getNome(), clienteDTO.getNome());
        assertEquals(clienteEntityOptional.get().getEmail(), clienteDTO.getEmail());
        assertEquals(clienteEntityOptional.get().getGenero(), clienteDTO.getGenero());
        assertEquals(clienteEntityOptional.get().getDataNascimento(), clienteDTO.getDataNascimento());
    }

    private static UsuarioEntity getUsuarioEntity() {
        UsuarioEntity usuarioEntity = new UsuarioEntity();
        usuarioEntity.setDataNascimento(LocalDate.of(1991, 9, 8));
        usuarioEntity.setEmail("maicon@teste.com.br");
        usuarioEntity.setNome("Maicon");
        usuarioEntity.setCargo(new CargoEntity(2, CargosEnum.ofTipo(Roles.PREMIUM), Set.of()));
        usuarioEntity.setIdUsuario(10);
        usuarioEntity.setGenero("M");
        usuarioEntity.setEnable(true);
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

    private PlayListDTO getPlaylistDTO(){
        return new PlayListDTO(1, "nomeDoido", Arrays.asList());
    }

}
