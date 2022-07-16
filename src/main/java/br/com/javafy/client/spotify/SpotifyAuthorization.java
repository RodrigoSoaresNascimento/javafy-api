package br.com.javafy.client.spotify;

import br.com.javafy.config.FeignConfig;
import br.com.javafy.service.dto.spotify.TokenDTO;
import feign.HeaderMap;
import feign.Headers;
import feign.Param;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;

import java.util.Map;

@FeignClient(
        name = "spotify-authorization",
        url = "https://accounts.spotify.com",
        configuration = FeignConfig.class
)
public interface SpotifyAuthorization {

    @RequestLine("POST /api/token")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    public TokenDTO authorization(@HeaderMap Map<String, String> headers,
                                  @Param("grant_type") String body);
}

