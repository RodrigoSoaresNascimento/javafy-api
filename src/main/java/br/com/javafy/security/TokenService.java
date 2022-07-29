package br.com.javafy.security;

import br.com.javafy.entity.UsuarioEntity;
import br.com.javafy.service.UsuarioService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
@RequiredArgsConstructor
public class TokenService {

    private static final String TOKEN_PREFIX = "Bearer ";

    private static final String KEY_CARGOS = "cargos";

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private String expiration;

    public String getToken(UsuarioEntity usuario){
        final Date now = new Date();
        final Date exp = new Date(now.getTime() + Long.parseLong(expiration));

        List<String> cargos = usuario.getCargos().stream()
                                .map(c -> c.getNome().getTipoCargo())
                                .toList();

        String token =  Jwts.builder()
                .setIssuer("vemser-api")
                .claim(Claims.ID, usuario.getIdUsuario())
                .claim(KEY_CARGOS, cargos)
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
        return TOKEN_PREFIX + token;
    }

    public UsernamePasswordAuthenticationToken isValid(String token){
        if(token == null) {
            return null;
        }
        Claims body = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                .getBody();

        Integer idUsuario = body.get(Claims.ID, Integer.class);

        if(idUsuario != null){
            List<String> cargos = body.get(KEY_CARGOS, List.class);
            List<SimpleGrantedAuthority> cargosGrantedAuthority = cargos.stream()
                    .map(cargo -> new SimpleGrantedAuthority(cargo))
                    .toList();
            return new UsernamePasswordAuthenticationToken(
                    idUsuario,
                    null,
                    cargosGrantedAuthority
            );
        }
        return null;
    }

}
