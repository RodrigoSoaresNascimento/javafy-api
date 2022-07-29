package br.com.javafy.enums;

import java.util.Arrays;

public enum CargosEnum {

    ROLE_FREE(Roles.FREE),
    ROLE_PREMIUM(Roles.PREMIUM),
    ROLE_ADMIN(Roles.ADMIN);

    private String tipoCargo;

    CargosEnum(String tipo){
        tipoCargo = tipo;
    }

    public String getTipoCargo(){
        return tipoCargo;
    }

    public static CargosEnum offTipo(String tipoCargo){
        return Arrays.stream(CargosEnum.values())
                .filter(tp -> tp.getTipoCargo().equals(tipoCargo))
                .findFirst()
                .get();
    }

}
