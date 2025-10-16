package org.com.imaapi.application.dto.usuario.output;

import org.com.imaapi.domain.model.Telefone;

public class TelefoneOutput {
    private Integer idTelefone;
    private String ddd;
    private String prefixo;
    private String sufixo;
    private Boolean whatsapp;

    public TelefoneOutput() {}

    public TelefoneOutput(Integer idTelefone, String ddd, String prefixo, String sufixo, Boolean whatsapp) {
        this.idTelefone = idTelefone;
        this.ddd = ddd;
        this.prefixo = prefixo;
        this.sufixo = sufixo;
        this.whatsapp = whatsapp;
    }

    public static TelefoneOutput fromEntity(Telefone telefone) {
        return new TelefoneOutput(
            telefone.getIdTelefone(),
            telefone.getDdd(),
            telefone.getPrefixo(),
            telefone.getSufixo(),
            telefone.getWhatsapp()
        );
    }

    public Integer getIdTelefone() {
        return idTelefone;
    }

    public void setIdTelefone(Integer idTelefone) {
        this.idTelefone = idTelefone;
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