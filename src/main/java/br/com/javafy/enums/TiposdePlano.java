package br.com.javafy.enums;

import java.util.Arrays;

public enum TiposdePlano {
    PREMIUM("premium"),
    FREE("free");

    private TiposdePlano tipo;
    TiposdePlano(String i) {
    }

    private TiposdePlano getTipo() {
        return this.tipo;
    }
    public static TiposdePlano ofTipo(String tipo){
        return Arrays.stream(TiposdePlano.values())
                .filter(tp -> tp.getTipo().equals(tipo))
                .findFirst()
                .get();
    }
}
