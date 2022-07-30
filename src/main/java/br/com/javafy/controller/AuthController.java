package br.com.javafy.controller;

import br.com.javafy.dto.LoginDTO;
import br.com.javafy.dto.usuario.UsuarioCreateDTO;
import br.com.javafy.dto.usuario.UsuarioDTO;
import br.com.javafy.dto.usuario.UsuarioLoginDTO;
import br.com.javafy.dto.usuario.UsuarioUpdateLoginDTO;
import br.com.javafy.entity.UsuarioEntity;
import br.com.javafy.enums.CargosEnum;
import br.com.javafy.exceptions.PessoaException;
import br.com.javafy.security.TokenService;
import br.com.javafy.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UsuarioService usuarioService;

    private final TokenService tokenService;

    private final AuthenticationManager authenticationManager;


    @PostMapping
    public String auth(@RequestBody @Valid LoginDTO login){

        UsernamePasswordAuthenticationToken userPassAuthToken =
                new UsernamePasswordAuthenticationToken(
                        login.getLogin(),
                        login.getSenha());

        Authentication authentication =
                authenticationManager.authenticate(userPassAuthToken);

        Object usuarioLogado =  authentication.getPrincipal();
        UsuarioEntity usuarioEntity = (UsuarioEntity) usuarioLogado;

        return tokenService.getToken(usuarioEntity);
    }

    @GetMapping("/get-islogged")
    public ResponseEntity<UsuarioLoginDTO> getUser () throws PessoaException {
        return ResponseEntity.ok(usuarioService.getLoggedUser());
    }

    @PostMapping("/create-user")
    public ResponseEntity<UsuarioDTO> create(
            @RequestBody UsuarioCreateDTO usuarioCreateDTO,
            CargosEnum cargos){
        return ResponseEntity.ok(usuarioService.create(usuarioCreateDTO, cargos));
    }

    @PutMapping("/update-credenciais")
    public ResponseEntity<UsuarioUpdateLoginDTO> update
            (@RequestBody UsuarioUpdateLoginDTO usuarioUpdateLoginDTO) throws PessoaException {
        return ResponseEntity.ok(usuarioService.updateLogin(usuarioUpdateLoginDTO));
    }

    // TODO -> TEM QUE CRIAR OUTRO MÉTODO PARA REMOVER OUTROS USUÁRIOS
    @DeleteMapping("/remover-usuario")
    public void delete (@PathVariable("idUser") Integer idUsuario) throws PessoaException {
        //usuarioService.delete();
    }

}
