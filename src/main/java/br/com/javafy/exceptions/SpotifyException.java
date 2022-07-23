package br.com.javafy.exceptions;

import java.sql.SQLException;

public class SpotifyException extends SQLException {
    public SpotifyException(String menssage) {
        super(menssage);
    }
}
