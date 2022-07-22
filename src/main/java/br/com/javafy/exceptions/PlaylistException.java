package br.com.javafy.exceptions;

import java.sql.SQLException;

public class PlaylistException extends SQLException {
    public PlaylistException(String menssage) {
        super(menssage);
    }
}
