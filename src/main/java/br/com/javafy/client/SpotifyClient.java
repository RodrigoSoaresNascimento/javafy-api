package br.com.javafy.client;

import br.com.javafy.dto.spotify.TokenDTO;
import feign.HeaderMap;
import feign.Headers;
import feign.Param;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;

import java.util.Map;

@FeignClient(
        name = "spotifyclient",
        url = "https://accounts.spotify.com"
)
public interface SpotifyClient {

    @RequestLine("POST /api/token")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    public TokenDTO autorization(@HeaderMap Map<String, String> headers, @Param("grant_type") String body);

    @RequestLine("GET /v1/tracks/{id}")
    @Headers("Content-Type: application/json")
    public void getMusica(@HeaderMap Map<String, String> headers,
                                         @Param("id") String idMusica);

}
