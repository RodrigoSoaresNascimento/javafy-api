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

    public static CargosEnum ofTipo(String tipoCargo){
        return Arrays.stream(CargosEnum.values())
                .filter(tp -> tp.getTipoCargo().equals(tipoCargo))
                .findFirst()
                .get();
    }

}
