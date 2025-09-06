package org.com.imaapi.application.dto.usuario.input;

public class TelefoneInputDTO {
    private String ddd;
    private String prefixo;
    private String sufixo;
    private Boolean whatsapp;

    public TelefoneInputDTO() {}

    public TelefoneInputDTO(String ddd, String prefixo, String sufixo, Boolean whatsapp) {
        this.ddd = ddd;
        this.prefixo = prefixo;
        this.sufixo = sufixo;
        this.whatsapp = whatsapp;
    }

    public String getDdd() {
        return ddd;
    }

    public void setDdd(String ddd) {
        this.ddd = ddd;
    }

    public String getPrefixo() {
        return prefixo;
    }

    public void setPrefixo(String prefixo) {
        this.prefixo = prefixo;
    }

    public String getSufixo() {
        return sufixo;
    }

    public void setSufixo(String sufixo) {
        this.sufixo = sufixo;
    }

    public Boolean getWhatsapp() {
        return whatsapp;
    }

    public void setWhatsapp(Boolean whatsapp) {
        this.whatsapp = whatsapp;
    }
}