package br.com.javafy.client.spotify;

import br.com.javafy.dto.spotify.MusicaFullDTO;
import feign.HeaderMap;
import feign.Headers;
import feign.Param;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;

import java.util.List;
import java.util.Map;

@FeignClient(
        name = "spotify-client",
        url = "https://api.spotify.com"
)
@Headers("Content-Type: application/json")
public interface SpotifyClient {

    @RequestLine("GET /v1/tracks/{id}")
    public MusicaFullDTO getTrack(@HeaderMap Map<String, String> headers,
                                  @Param("id") String id);

    @RequestLine("GET /v1/tracks/?ids={ids}")
    public Map<String, List<MusicaFullDTO>> getTracks(@HeaderMap Map<String, String> headers,
                                                      @Param String ids);

    @RequestLine("GET /v1/search?q={query}&type={type}&limit=20")
    public Map<String, Map<String, Object>> search(@HeaderMap Map<String, String> headers,
                     @Param String query, @Param String type );

}
