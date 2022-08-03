package br.com.javafy;

import br.com.javafy.dto.usuario.UsuarioDTO;
import br.com.javafy.entity.CargoEntity;
import br.com.javafy.entity.UsuarioEntity;
import br.com.javafy.exceptions.PessoaException;
import br.com.javafy.exceptions.SeguidoresException;
import br.com.javafy.repository.UsuarioRepository;
import br.com.javafy.service.SeguidoresService;
import br.com.javafy.service.UsuarioService;
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
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SeguidoresServiceTest {

    @InjectMocks
    private SeguidoresService seguidoresService;

    @Mock
    private UsuarioService usuarioService;

    @Mock
    private UsuarioRepository usuarioRepository;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void init() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        ReflectionTestUtils.setField(seguidoresService, "objectMapper", objectMapper);
    }

    @Test
    public void deveTestarGetAllSeguidoresComSucesso() throws PessoaException {
        UsuarioEntity usuario = usuarioEntity1();
        usuario.setSeguidores(Set.of(usuarioEntity2()));

        when(usuarioService.retornarUsuarioEntityById()).thenReturn(usuario);
        List<UsuarioDTO> seguidores = seguidoresService.getAllSeguidores();

        assertNotNull(seguidores);
        assertFalse(seguidores.isEmpty());
    }

    @Test
    public void deveTestarGetAllSeguindoComSucesso() throws PessoaException {
        UsuarioEntity usuario = usuarioEntity1();
        usuario.setSeguindo(Set.of(usuarioEntity2()));

        when(usuarioService.retornarUsuarioEntityById()).thenReturn(usuario);
        List<UsuarioDTO> seguindo = seguidoresService.getAllSeguindo();

        assertNotNull(seguindo);
        assertFalse(seguindo.isEmpty());
    }

    @Test
    public void deveTestarSeguirUserComSucesso() throws PessoaException, SeguidoresException {
        UsuarioEntity usuario1 = usuarioEntity1();
        UsuarioEntity usuario2 = usuarioEntity2();

        when(usuarioService.retornarUsuarioEntityById()).thenReturn(usuario1);
        when(usuarioService.buscarOutroUsuario(anyInt())).thenReturn(usuario2);
        boolean deuCerto = seguidoresService.seguirUser(usuario2.getIdUsuario());
        usuarioRepository.save(usuario1);


        assertNotNull(usuario1.getSeguidores());
        assertTrue(deuCerto);
        assertFalse(usuario1.getSeguidores().isEmpty());
    }

    @Test
    public void deixaDeSeguirUserComSucesso() throws PessoaException, SeguidoresException {
        UsuarioEntity usuario1 = usuarioEntity1();
        UsuarioEntity usuario2 = usuarioEntity2();

        when(usuarioService.retornarUsuarioEntityById()).thenReturn(usuario1);
        when(usuarioService.buscarOutroUsuario(anyInt())).thenReturn(usuario2);
        boolean deuCerto = seguidoresService.deixarDeSeguirUsuario(usuario2.getIdUsuario());
        usuarioRepository.save(usuario2);


        assertTrue(usuario1.getSeguindo().isEmpty());
        assertTrue(deuCerto);
        assertTrue(usuario1.getSeguidores().isEmpty());
    }

    @Test(expected = PessoaException.class)
    public void deveTestarSeguirUserSemId() throws PessoaException {
        UsuarioEntity usuario1 = usuarioEntity1();

        doThrow(new PessoaException("Erro ao salvar")).when(usuarioService).retornarUsuarioEntityById();

        seguidoresService.seguirUser(usuario1.getIdUsuario());
    }

    private static UsuarioEntity usuarioEntity1() {
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setIdUsuario(1);
        usuario.setNome("Bruno");
        usuario.setLogin("bruno");
        usuario.setEmail("brunoroliveira_@outlook.com");
        usuario.setSenha("123456");
        usuario.setGenero("M");
        usuario.setDataNascimento(LocalDate.of(1997, 12, 31));
        usuario.setCargo(new CargoEntity());
        return usuario;
    }

    private static UsuarioEntity usuarioEntity2() {
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setIdUsuario(2);
        usuario.setNome("Roger");
        usuario.setLogin("roger");
        usuario.setEmail("roger@outlook.com");
        usuario.setSenha("123456");
        usuario.setGenero("M");
        usuario.setDataNascimento(LocalDate.of(1997, 5, 10));
        usuario.setCargo(new CargoEntity());
        usuario.setSeguidores(Set.of(usuarioEntity1()));
        return usuario;
    }
}

