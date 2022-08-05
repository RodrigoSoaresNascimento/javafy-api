package br.com.javafy.service;

import br.com.javafy.dto.PageDTO;
import br.com.javafy.dto.usuario.*;
import br.com.javafy.entity.CargoEntity;
import br.com.javafy.entity.UsuarioEntity;
import br.com.javafy.enums.CargosEnum;
import br.com.javafy.enums.CargosUser;
import br.com.javafy.enums.TipoDeMensagem;
import br.com.javafy.exceptions.PessoaException;
import br.com.javafy.repository.CargoRepository;
import br.com.javafy.repository.UsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.Md4PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final ObjectMapper objectMapper;
    private final EmailService emailService;
    private final CargoRepository cargoRepository;

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

    public UsuarioDTO create(UsuarioCreateDTO usuario, CargosEnum cargo)  {
        UsuarioEntity usuarioEntity = converterUsuarioEntity(usuario);
        usuarioEntity.setCargo(cargoRepository.findByNome(cargo));
        usuarioEntity.setSenha(encodePassword(usuario.getSenha()));
        usuarioEntity.setEnable(true);
        usuarioEntity = usuarioRepository.save(usuarioEntity);
        UsuarioDTO usuarioDTO= converterUsuarioDTO(usuarioEntity);
        emailService.sendEmail(usuarioDTO, TipoDeMensagem.CREATE.getTipoDeMensagem());
        return usuarioDTO;
    }

    public UsuarioDTO update(UsuarioUpdateDTO usuarioUpdate, CargosUser cargo)
            throws PessoaException {

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

    public void restrigirUsuario(Integer idUsuario)
            throws PessoaException {
        UsuarioEntity usuarioEntity = buscarOutroUsuario(idUsuario);
        usuarioEntity.setEnable(false);
        usuarioRepository.save(usuarioEntity);
    }

    public List<UsuarioRelatorioDTO> relatorio (){
        return usuarioRepository.relatorioPessoa();
    }

}
