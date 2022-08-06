package br.com.javafy.service;

import br.com.javafy.dto.PageDTO;
import br.com.javafy.dto.usuario.*;
import br.com.javafy.entity.CargoEntity;
import br.com.javafy.entity.UsuarioEntity;
import br.com.javafy.enums.CargosEnum;
import br.com.javafy.enums.CargosUser;
import br.com.javafy.enums.Roles;
import br.com.javafy.exceptions.ComentarioNaoCadastradoException;
import br.com.javafy.exceptions.PessoaException;
import br.com.javafy.exceptions.PlaylistException;
import br.com.javafy.repository.CargoRepository;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
public class UsuarioServiceTest {

    @InjectMocks
    private UsuarioService usuarioService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private CargoRepository cargoRepository;

    @Mock
    private EmailService emailService;


    @Before
    public void init() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        ReflectionTestUtils.setField(usuarioService, "objectMapper", objectMapper);
    }

    @Test
    public void deveTestarBuscarPessoaPeloId() throws PessoaException {
        UsuarioEntity usuario = getUsuarioEntity();

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(
                        123,
                        null
                );

        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);


        when(usuarioRepository.findById(anyInt())).thenReturn(Optional.of(usuario));

        UsuarioDTO usuarioDTO = usuarioService.findById();

        assertNotNull(usuarioDTO);
    }

    @Test(expected = PessoaException.class)
    public void deveGetIdLoggedUser() throws PessoaException {

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(
                        "123",
                        null
                );

        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);


        usuarioService.getIdLoggedUser();
    }

    @Test
    public void deveTestarListComSucesso() {

        // setup

        List<UsuarioEntity> usuarios = new ArrayList<>();
        usuarios.add(getUsuarioEntity());

        // act
        when(usuarioRepository.findAll()).thenReturn(usuarios);

        // assert
        List<UsuarioDTO> usuarioDTOS = usuarioService.list();

        assertNotNull(usuarioDTOS);
        assertFalse(usuarioDTOS.isEmpty());

    }

    @Test
    public void deveTestarCriarUsuarioComSucesso() throws ComentarioNaoCadastradoException, PessoaException, PlaylistException {

        // setup
        UsuarioEntity usuario = getUsuarioEntity();

        // act
        when(usuarioRepository.save(any(UsuarioEntity.class))).thenReturn(usuario);

        UsuarioDTO usuarioDTO = usuarioService.create(getUsuarioCreateDto(), CargosEnum.ROLE_ADMIN);
        // assert

        assertNotNull(usuarioDTO);
        assertEquals(usuarioDTO.getNome(), usuario.getNome());
        assertEquals(usuarioDTO.getDataNascimento(), usuario.getDataNascimento());
        assertEquals(usuarioDTO.getEmail(), usuario.getEmail());
        assertEquals(usuarioDTO.getLogin(), usuario.getLogin());
        assertEquals(usuarioDTO.getGenero(), usuario.getGenero());

    }

    @Test
    public void deveTestarUpdateUsuarioComSucesso() throws PessoaException {

        // setup
        UsuarioEntity usuario = getUsuarioEntity();
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(
                        123,
                        null
                );

        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        CargosUser cargosEnum = CargosUser.ofTipo(Roles.PREMIUM);
        CargoEntity cargoEntity = new CargoEntity(2, CargosEnum.ofTipo(Roles.PREMIUM), Set.of());
        // act
        when(usuarioRepository.findById(anyInt())).thenReturn(Optional.of(usuario));
        when(usuarioRepository.save(any(UsuarioEntity.class))).thenReturn(usuario);
        when(cargoRepository.findByNome(any())).thenReturn(cargoEntity);

        // assert

        UsuarioDTO usuarioDTO = usuarioService.update(getUsuarioUpdateDto(), cargosEnum);

        assertNotNull(usuarioDTO);
        assertEquals(usuarioDTO.getIdUsuario(), usuario.getIdUsuario());
        assertEquals(usuarioDTO.getNome(), usuario.getNome());
        assertEquals(usuarioDTO.getGenero(), usuario.getGenero());
        assertEquals(usuarioDTO.getLogin(), usuario.getLogin());
        assertEquals(usuarioDTO.getDataNascimento(), usuario.getDataNascimento());

    }

    @Test
    public void deveTestarUsuarioPaginadoComSucesso() {
        // setup
        List<UsuarioEntity> usuarios = List.of(getUsuarioEntity());
        Page<UsuarioEntity> pageUsuarios = new PageImpl<>(usuarios);

        when(usuarioRepository.findAll(any(Pageable.class)))
                .thenReturn(pageUsuarios);

        // act
        PageDTO<UsuarioDTO> paginaDeUsuarios = usuarioService
                .listarUsuariosPorNomePaginado(1000, 3);

        // assert
        assertNotNull(paginaDeUsuarios);
        assertEquals(1, pageUsuarios.getTotalPages());
        assertEquals(1, paginaDeUsuarios.getContent().size());
    }

    @Test
    public void deveTestarGetLoggedComSucesso() throws PessoaException {
        UsuarioEntity usuario = getUsuarioEntity();

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(
                        123,
                        null
                );

        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

        when(usuarioRepository.findById(anyInt())).thenReturn(Optional.of(usuario));

        UsuarioLoginDTO usuarioDTO = usuarioService.getLoggedUser();

        assertNotNull(usuarioDTO);
        assertEquals(usuarioDTO.getEmail(), usuario.getEmail());
        assertEquals(usuarioDTO.getLogin(), usuario.getLogin());

    }

    @Test(expected = PessoaException.class)
    public void deveTestarGetLoggedSemSucesso() throws PessoaException {
        UsuarioEntity usuario = getUsuarioEntity();

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(
                        "123",
                        null
                );

        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

        UsuarioLoginDTO usuarioDTO = usuarioService.getLoggedUser();

    }

    @Test(expected = PessoaException.class )
    public void deveNaoRestringirUsuarioException() throws PessoaException {
        when(usuarioRepository.findById(anyInt())).thenReturn(Optional.empty());
        usuarioService.restrigirUsuario(anyInt());
        verify(usuarioRepository, times(0))
                .save(any(UsuarioEntity.class));
    }


    @Test
    public void deveRestringirUsuario() throws PessoaException {
        UsuarioEntity usuario = getUsuarioAdmin();
        when(usuarioRepository.findById(anyInt())).thenReturn(Optional.of(usuario));

        usuarioService.restrigirUsuario(anyInt());
        verify(usuarioRepository, times(1))
                .save(any(UsuarioEntity.class));
    }

    @Test
    public void deveAtualizarAsCredenciasDoUsuario () throws PessoaException {

        UsuarioEntity usuario = getUsuarioEntity();

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(
                        123,
                        null
                );

        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

        when(usuarioRepository.save(any(UsuarioEntity.class))).thenReturn(usuario);
        when(usuarioRepository.findById(anyInt())).thenReturn(Optional.of(usuario));
        UsuarioUpdateLoginDTO usuarioLoginDTO = usuarioService.updateLogin(getUsuarioUpdateLogin());

        assertNotNull(usuarioLoginDTO);
        assertEquals(usuarioLoginDTO.getLogin(), usuario.getLogin());
        assertEquals(usuarioLoginDTO.getSenha(), usuario.getSenha());

    }

    @Test
    public void deveDeletarAContaDoUsuarioLogado () throws PessoaException {

        UsuarioEntity usuario = getUsuarioEntity();

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(
                        123,
                        null
                );

        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

        when(usuarioRepository.findById(anyInt())).thenReturn(Optional.of(usuario));
        doNothing().when(usuarioRepository).delete(any(UsuarioEntity.class));
        usuarioService.delete();

        verify(usuarioRepository, times(1)).delete(any(UsuarioEntity.class));

    }

    @Test
    public void deveTrazerRelatorioDoUsuarioComSucesso () {

        UsuarioRelatorioDTO usuarioRelatorioDTO = new UsuarioRelatorioDTO();
        usuarioRelatorioDTO.setEmail(getUsuarioEntity().getEmail());
        usuarioRelatorioDTO.setNome(getUsuarioEntity().getNome());
        usuarioRelatorioDTO.setNomePlaylist("Minhas musicas");

        List<UsuarioRelatorioDTO> relatorio = List.of(usuarioRelatorioDTO);

        when(usuarioRepository.relatorioPessoa()).thenReturn(relatorio);

        List<UsuarioRelatorioDTO> relatorioPessoa = usuarioService.relatorio();

        assertNotNull(relatorioPessoa);
        assertFalse(relatorioPessoa.isEmpty());


    }

    @Test
    public void deveTestarFindByLoginComSucesso () {

        UsuarioEntity usuario = getUsuarioEntity();

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(
                        123,
                        null
                );

        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

        when(usuarioRepository.findByLogin(anyString())).thenReturn(Optional.of(usuario));

        Optional<UsuarioEntity> usuarioEntity = usuarioService.findByLogin(usuario.getLogin());

        assertNotNull(usuarioEntity);
        assertEquals(usuarioEntity.get().getLogin(), usuario.getLogin());


    }

    private static UsuarioDTO getUsuarioDTO() {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setDataNascimento(LocalDate.of(1991, 9, 8));
        usuarioDTO.setEmail("maicon@teste.com.br");
        usuarioDTO.setNome("Maicon");
        usuarioDTO.setIdUsuario(10);
        usuarioDTO.setGenero("M");
        usuarioDTO.setSenha("123");
        usuarioDTO.setLogin("user1");
        return usuarioDTO;
    }

    private static UsuarioUpdateLoginDTO getUsuarioUpdateLogin () {
        UsuarioUpdateLoginDTO usuarioUpdateLoginDTO = new UsuarioUpdateLoginDTO();
        usuarioUpdateLoginDTO.setLogin("Maicon");
        usuarioUpdateLoginDTO.setSenha("123");
        return usuarioUpdateLoginDTO;
    }

    private static UsuarioLoginDTO getUsuarioLogin () {
        UsuarioLoginDTO usuarioLoginDTO = new UsuarioLoginDTO();
        usuarioLoginDTO.setLogin("Maicon");
        usuarioLoginDTO.setEmail("maicon@teste.com.br");
        return usuarioLoginDTO;
    }

    private static UsuarioCreateDTO getUsuarioCreateDto() {
        UsuarioCreateDTO usuarioDTO = new UsuarioCreateDTO();
        usuarioDTO.setDataNascimento(LocalDate.of(1991, 9, 8));
        usuarioDTO.setEmail("maicon@teste.com.br");
        usuarioDTO.setNome("Maicon");
        usuarioDTO.setGenero("M");
        usuarioDTO.setSenha("123");
        usuarioDTO.setLogin("user1");
        return usuarioDTO;
    }

    private static UsuarioUpdateDTO getUsuarioUpdateDto() {
        UsuarioUpdateDTO usuarioDTO = new UsuarioUpdateDTO();
        usuarioDTO.setDataNascimento(LocalDate.of(1991, 9, 8));
        usuarioDTO.setEmail("maicon@teste.com.br");
        usuarioDTO.setNome("Maicon");
        usuarioDTO.setGenero("M");
        return usuarioDTO;
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

}
