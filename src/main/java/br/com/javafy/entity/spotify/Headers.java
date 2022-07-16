package br.com.javafy.entity.spotify;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Component
public class Headers {

    @Value("basic " + "${spotify-client}")
    private String autorization ;

    @Value("client_credentials")
    private String grantType;

    public Map<String, String> toDados(){
        return new HashMap<>(Map.of("Authorization", autorization));
    }

}
