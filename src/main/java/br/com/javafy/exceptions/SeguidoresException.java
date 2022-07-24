package br.com.javafy.exceptions;

import java.sql.SQLException;

public class SeguidoresException extends SQLException {
    public SeguidoresException(String menssage) {
        super(menssage);
    }
}
