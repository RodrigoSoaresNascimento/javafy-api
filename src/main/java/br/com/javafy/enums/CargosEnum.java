package br.com.javafy.enums;

import java.util.Arrays;

public enum CargosEnum {

    FREE("ROLE_FREE"),
    PREMIUM("ROLE_PREMIUM"),
    ADMIN("ROLE_ADMIN");

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
