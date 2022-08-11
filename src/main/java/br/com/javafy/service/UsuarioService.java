package br.com.javafy.service;

import br.com.javafy.dto.PageDTO;
import br.com.javafy.dto.email.EmailDTO;
import br.com.javafy.dto.usuario.*;
import br.com.javafy.entity.CargoEntity;
import br.com.javafy.entity.UsuarioEntity;
import br.com.javafy.enums.CargosEnum;
import br.com.javafy.enums.CargosUser;
import br.com.javafy.enums.ControllerUserEnable;
import br.com.javafy.enums.TipoDeMensagem;
import br.com.javafy.exceptions.PessoaException;
import br.com.javafy.repository.CargoRepository;
import br.com.javafy.repository.UsuarioRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.Md4PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final ObjectMapper objectMapper;
    private final CargoRepository cargoRepository;
    private final ProduceEmailService produceEmailService;

    public UsuarioEntity converterUsuarioEntity(UsuarioCreateDTO usuarioCreateDTO) {
        return objectMapper.convertValue(usuarioCreateDTO, UsuarioEntity.class);
    }

    public UsuarioDTO converterUsuarioDTO(UsuarioEntity usuario) {
        return objectMapper.convertValue(usuario, UsuarioDTO.class);
    }

    private String encodePassword(String password){
        return new Md4PasswordEncoder().encode(password);
    }

    public UsuarioEntity buscarOutroUsuario(Integer idUser) throws PessoaException {
        return usuarioRepository
                .findById(idUser)
                .orElseThrow(() -> new PessoaException("Usuário não cadastrado"));
    }

    public Integer getIdLoggedUser() throws PessoaException {
        Integer idUser;
        try {
            idUser =  (Integer) SecurityContextHolder.getContext()
                    .getAuthentication().getPrincipal();
        } catch (Exception e){
            throw new PessoaException("Usuário não logado");
        }
        return idUser;
    }

    public Optional<UsuarioEntity> findByLogin(String login){
        return usuarioRepository.findByLogin(login);
    }

    public UsuarioLoginDTO getLoggedUser()
            throws PessoaException {
        return objectMapper.convertValue(retornarUsuarioEntityById(), UsuarioLoginDTO.class);
    }

    public UsuarioEntity retornarUsuarioEntityById()
            throws PessoaException {
        return usuarioRepository
                .findById(getIdLoggedUser())
                .orElseThrow(() -> new PessoaException("Usuário não cadastrado"));
    }

    public UsuarioDTO findById()
            throws PessoaException {
        return converterUsuarioDTO(retornarUsuarioEntityById());
    }

    public PageDTO<UsuarioDTO> listarUsuariosPorNomePaginado(Integer pagina, Integer registro){
        PageRequest pageRequest = PageRequest.of(pagina, registro);
        Page<UsuarioEntity> page = usuarioRepository.findAll(pageRequest);
        List<UsuarioDTO> usuarioDTOS = page.getContent().stream()
                .map(usuarioEntity -> objectMapper.convertValue(usuarioEntity, UsuarioDTO.class))
                .toList();
        return new PageDTO<>(page.getTotalElements(), page.getTotalPages(), pagina, registro, usuarioDTOS);
    }

    public List<UsuarioDTO> list()  {
        return usuarioRepository
                .findAll()
                .stream()
                .map(this::converterUsuarioDTO)
                .toList();
    }

    public UsuarioDTO create(UsuarioCreateDTO usuario, CargosEnum cargo) throws JsonProcessingException {
        UsuarioEntity usuarioEntity = converterUsuarioEntity(usuario);
        usuarioEntity.setCargo(cargoRepository.findByNome(cargo));
        usuarioEntity.setSenha(encodePassword(usuario.getSenha()));
        usuarioEntity.setEnable(true);
        usuarioEntity = usuarioRepository.save(usuarioEntity);
        UsuarioDTO usuarioDTO= converterUsuarioDTO(usuarioEntity);
        EmailDTO emailDTO = objectMapper.convertValue(usuarioDTO, EmailDTO.class);
        emailDTO.setTipoDeMensagem(TipoDeMensagem.CREATE);
        emailDTO.setMensagem("Bem vindo ao javafy, sua rede de contatos baseados em musica!");
        produceEmailService.enviarMensage(emailDTO);
        return usuarioDTO;
    }

    public UsuarioDTO update(UsuarioUpdateDTO usuarioUpdate, CargosUser cargo)
            throws PessoaException, JsonProcessingException {

        UsuarioEntity usuarioEntity = retornarUsuarioEntityById();

        if(cargo != null){
            CargosEnum cargosEnum = CargosEnum.ofTipo(cargo.getTipoCargo());
            CargoEntity cargoEntity = cargoRepository.findByNome(cargosEnum);
            usuarioEntity.setCargo(cargoEntity);
        }

        if(usuarioUpdate.getEmail() != null){
            usuarioEntity.setEmail(usuarioUpdate.getEmail());
        }

        if(usuarioUpdate.getGenero() != null){
            usuarioEntity.setGenero(usuarioUpdate.getGenero());
        }

        if(usuarioUpdate.getDataNascimento() != null){
            usuarioEntity.setDataNascimento(usuarioUpdate.getDataNascimento());
        }

        if(usuarioUpdate.getNome() != null) {
            usuarioEntity.setNome(usuarioUpdate.getNome());
        }
        System.out.println(usuarioEntity);
        usuarioEntity = usuarioRepository.save(usuarioEntity);
        EmailDTO emailDTO = objectMapper.convertValue(usuarioEntity, EmailDTO.class);
        emailDTO.setTipoDeMensagem(TipoDeMensagem.UPDATE);
        emailDTO.setMensagem("Usuario atualizado com sucesso!");
        produceEmailService.enviarMensage(emailDTO);
        return converterUsuarioDTO(usuarioEntity);
    }

    public UsuarioUpdateLoginDTO updateLogin (UsuarioUpdateLoginDTO usuario)
            throws PessoaException {
        UsuarioEntity usuarioEntity = retornarUsuarioEntityById();

        if(usuario.getLogin() != null){
            usuarioEntity.setLogin(usuario.getLogin());
        }

        if(usuario.getSenha() != null){
            usuarioEntity.setSenha(encodePassword(usuario.getSenha()));
        }

        usuarioRepository.save(usuarioEntity);
        return objectMapper.convertValue(usuarioEntity, UsuarioUpdateLoginDTO.class);

    }

    public void delete()
            throws PessoaException {
        UsuarioEntity usuario = retornarUsuarioEntityById();
        usuarioRepository.delete(usuario);
    }

    public void controlarAcessoUsuario (Integer idUsuario, ControllerUserEnable userEnable)
            throws PessoaException, JsonProcessingException {
        boolean habilitado = userEnable.getEnable() == 1;
        UsuarioEntity usuarioEntity = buscarOutroUsuario(idUsuario);
        usuarioEntity.setEnable(habilitado);
        if(!habilitado){
            EmailDTO emailDTO = objectMapper.convertValue(usuarioEntity, EmailDTO.class);
            emailDTO.setTipoDeMensagem(TipoDeMensagem.DELETE);
            emailDTO.setMensagem("Você perdeu acesso a sua conta javafy, por favor entrar em contato" +
                    " com o suporte@javafy");
            produceEmailService.enviarMensage(emailDTO);
        }
        usuarioRepository.save(usuarioEntity);
    }

    public List<UsuarioRelatorioDTO> relatorio (){
        return usuarioRepository.relatorioPessoa();
    }

    @Scheduled(cron = "0 1 0 * * *")
    public void enviarEmailAniversario() throws JsonProcessingException {
        List<UsuarioDTO> usuarioDTO = listBirthDay();

        for (UsuarioDTO usuario : usuarioDTO){
            EmailDTO emailDTO = objectMapper.convertValue(usuario, EmailDTO.class);
            emailDTO.setTipoDeMensagem(TipoDeMensagem.BIRTHDAY);
            emailDTO.setMensagem("Enviar email de aniversário para o usuário!");
            produceEmailService.enviarMensage(emailDTO);
            log.info("Mensagem de email enviada");
        }
    }
    public List<UsuarioDTO> listBirthDay(){
        return usuarioRepository.findByBirthDay().stream()
                .map(this::converterUsuarioDTO)
                .toList();
    }
}
