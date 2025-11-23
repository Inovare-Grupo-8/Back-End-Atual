package org.com.imaapi.application.dto.consulta.input;

import java.time.LocalDate;

public class BuscarConsultasInput {
    private Integer userId;
    private String periodo; // "ATUAL", "DIA", "SEMANA", "MES"
    private LocalDate dataReferencia;
    
    public BuscarConsultasInput() {}
    
    public BuscarConsultasInput(Integer userId, String periodo, LocalDate dataReferencia) {
        this.userId = userId;
        this.periodo = periodo;
        this.dataReferencia = dataReferencia;
    }
    
    public Integer getUserId() {
        return userId;
    }
    
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    
    public String getPeriodo() {
        return periodo;
    }
    
    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }
    
    public LocalDate getDataReferencia() {
        return dataReferencia;
    }
    
    public void setDataReferencia(LocalDate dataReferencia) {
        this.dataReferencia = dataReferencia;
    }
}