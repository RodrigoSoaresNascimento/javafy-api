package br.com.javafy.enums;

public enum ControllerUserEnable {
    ATIVAR (1), DESATIVAR(0);

    private Integer enable;

    ControllerUserEnable(Integer enable){
        this.enable = enable;
    }

    public Integer getEnable(){
        return enable;
    }
}
