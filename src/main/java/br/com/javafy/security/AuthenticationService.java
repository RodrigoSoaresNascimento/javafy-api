package br.com.javafy.security;

import br.com.javafy.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements UserDetailsService {

    private final UsuarioService usuarioService;

    @Override
    public UserDetails loadUserByUsername(String userLogin) throws UsernameNotFoundException {
        return usuarioService.findByLogin(userLogin)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário Inválido"));
    }
}
