package br.com.javafy.service;

import br.com.javafy.dto.coments.ComentsCreateDTO;
import br.com.javafy.dto.coments.ComentsDTO;
import br.com.javafy.entity.CargoEntity;
import br.com.javafy.entity.ComentsEntity;
import br.com.javafy.entity.UsuarioEntity;
import br.com.javafy.enums.CargosEnum;
import br.com.javafy.enums.Roles;
import br.com.javafy.exceptions.PessoaException;
import br.com.javafy.repository.ComentsRepository;
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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ComentsServiceTest {

    @InjectMocks
    private ComentsService comentsService;

    @Mock
    ComentsRepository comentsRepository;
    @Mock
    UsuarioService usuarioService;

    @Mock
    UsuarioRepository usuarioRepository;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void init() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        ReflectionTestUtils.setField(comentsService, "objectMapper", objectMapper);
    }

    @Test
    public void deveListarTodosOsComentariosDaPostagem () {
        List<ComentsEntity> coments = new ArrayList<>();
        coments.add(getComentEntity());

        when(comentsRepository.findAll()).thenReturn(coments);

        List<ComentsDTO> comentsDTOS = comentsService.list();

        assertNotNull(comentsDTOS);
        assertFalse(comentsDTOS.isEmpty());

    }

    @Test
    public void deveCriarComentsDePostagemComSucesso () throws PessoaException {
        UsuarioEntity usuario = getUsuarioEntity();
        ComentsEntity comentsEntity = getComentEntity();

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(
                        123,
                        null
                );

        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

        when(usuarioService.retornarUsuarioEntityById()).thenReturn(usuario);
        when(comentsRepository.save(any(ComentsEntity.class))).thenReturn(comentsEntity);

        ComentsDTO comentsDTO = comentsService.create(getComentsDTO());

        assertNotNull(comentsDTO);
        assertEquals(comentsDTO.getBody(), comentsEntity.getBody());
        assertEquals(comentsDTO.getIdPosts(), comentsEntity.getIdPosts());

    }

    private static ComentsEntity getComentEntity () {
        ComentsEntity coments = new ComentsEntity();
        coments.setIdComents("1");
        coments.setBody("Texto sobre o artigo");
        coments.setIdUsuario(1);
        coments.setIdPosts("1");
        return coments;
    }

    private static ComentsCreateDTO getComentsDTO () {
        ComentsCreateDTO coments = new ComentsCreateDTO();
        coments.setBody("Texto sobre o artigo");
        coments.setIdPosts("1");
        return coments;
    }

    private static UsuarioEntity getUsuarioEntity() {
        UsuarioEntity usuarioEntity = new UsuarioEntity();
        usuarioEntity.setDataNascimento(LocalDate.of(1991, 9, 8));
        usuarioEntity.setEmail("maicon@teste.com.br");
        usuarioEntity.setNome("Maicon");
        usuarioEntity.setCargo(new CargoEntity(2, CargosEnum.ofTipo(Roles.PREMIUM), Set.of()));
        usuarioEntity.setIdUsuario(1);
        usuarioEntity.setGenero("M");
        usuarioEntity.setSenha("123");
        usuarioEntity.setLogin("user1");
        usuarioEntity.setEnable(true);
        return usuarioEntity;
    }

}
