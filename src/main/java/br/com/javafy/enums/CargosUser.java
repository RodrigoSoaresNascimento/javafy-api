package br.com.javafy.enums;

import java.util.Arrays;

public enum CargosUser {

    ROLE_FREE(Roles.FREE),
    ROLE_PREMIUM(Roles.PREMIUM);

    private final String tipoCargo;

    CargosUser(String tipo){
        tipoCargo = tipo;
    }

    public String getTipoCargo(){
        return tipoCargo;
    }

    public static CargosUser ofTipo(String tipoCargo){
        return Arrays.stream(CargosUser.values())
                .filter(tp -> tp.getTipoCargo().equals(tipoCargo))
                .findFirst()
                .get();
    }

}
