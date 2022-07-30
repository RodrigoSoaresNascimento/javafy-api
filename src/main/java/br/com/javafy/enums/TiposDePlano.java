package br.com.javafy.enums;

import java.util.Arrays;

public enum TiposDePlano {
    PREMIUM(0),
    FREE(1);

    private Integer tipo;
    TiposDePlano(Integer i) {
    }

    public Integer getTipo() {
        return this.tipo;
    }

    public static TiposDePlano ofTipo(Integer tipo){
        return Arrays.stream(TiposDePlano.values())
                .filter(tp -> tp.getTipo().equals(tipo))
                .findFirst()
                .get();
    }
}
