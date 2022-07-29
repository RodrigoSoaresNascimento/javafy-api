package br.com.javafy.enums;

import java.util.Arrays;

public enum CargosEnum {

    FREE(Roles.FREE),
    PREMIUM(Roles.PREMIUM),
    ADMIN(Roles.ADMIN);

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
