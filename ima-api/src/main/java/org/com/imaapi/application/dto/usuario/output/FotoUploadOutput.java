package org.com.imaapi.application.dto.usuario.output;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class FotoUploadOutput {
    
    private String mensagem;
    private String urlFoto;
    private String nomeArquivo;
    private Long tamanhoArquivo;
    private String tipoArquivo;
    private LocalDateTime dataUpload;
    private Boolean sucesso;
    
    public FotoUploadOutput() {}
    
    public FotoUploadOutput(String mensagem, String urlFoto) {
        this.mensagem = mensagem;
        this.urlFoto = urlFoto;
        this.sucesso = true;
        this.dataUpload = LocalDateTime.now();
    }
    
    public FotoUploadOutput(String mensagem, String urlFoto, String nomeArquivo, 
                           Long tamanhoArquivo, String tipoArquivo) {
        this.mensagem = mensagem;
        this.urlFoto = urlFoto;
        this.nomeArquivo = nomeArquivo;
        this.tamanhoArquivo = tamanhoArquivo;
        this.tipoArquivo = tipoArquivo;
        this.sucesso = true;
        this.dataUpload = LocalDateTime.now();
    }
    
    // Construtor para casos de erro
    public static FotoUploadOutput erro(String mensagem) {
        FotoUploadOutput output = new FotoUploadOutput();
        output.mensagem = mensagem;
        output.sucesso = false;
        output.dataUpload = LocalDateTime.now();
        return output;
    }
    
    // Construtor para sucesso com informações completas
    public static FotoUploadOutput sucesso(String mensagem, String urlFoto, String nomeArquivo, 
                                         Long tamanhoArquivo, String tipoArquivo) {
        return new FotoUploadOutput(mensagem, urlFoto, nomeArquivo, tamanhoArquivo, tipoArquivo);
    }
    
    public String getMensagem() {
        return mensagem;
    }
    
    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
    
    public String getUrlFoto() {
        return urlFoto;
    }
    
    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }
    
    public String getNomeArquivo() {
        return nomeArquivo;
    }
    
    public void setNomeArquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }
    
    public Long getTamanhoArquivo() {
        return tamanhoArquivo;
    }
    
    public void setTamanhoArquivo(Long tamanhoArquivo) {
        this.tamanhoArquivo = tamanhoArquivo;
    }
    
    public String getTipoArquivo() {
        return tipoArquivo;
    }
    
    public void setTipoArquivo(String tipoArquivo) {
        this.tipoArquivo = tipoArquivo;
    }
    
    public LocalDateTime getDataUpload() {
        return dataUpload;
    }
    
    public void setDataUpload(LocalDateTime dataUpload) {
        this.dataUpload = dataUpload;
    }
    
    public Boolean getSucesso() {
        return sucesso;
    }
    
    public void setSucesso(Boolean sucesso) {
        this.sucesso = sucesso;
    }
}