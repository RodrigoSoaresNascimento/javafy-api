package br.com.javafy.enums;

import java.util.Arrays;

public enum TipoDeMensagem {
    CREATE("create"),
    UPDATE("update"),
    DELETE("delete");

    private String tipoDeMensagem;

    TipoDeMensagem(String tipoDeMensagem) {
        this.tipoDeMensagem = tipoDeMensagem;
    }

    public static TipoDeMensagem tipo (String tipoMensagem){
        return Arrays.stream(TipoDeMensagem.values())
                .filter(tp -> tp.getTipoDeMensagem().equals(tipoMensagem))
                .findFirst()
                .get();
    }

    public String getTipoDeMensagem() {
        return tipoDeMensagem;
    }
}
