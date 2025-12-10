package org.com.imaapi.application.dto.usuario.input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

public class FotoUploadInput {

    @NotNull(message = "O ID do usuário é obrigatório")
    @Positive(message = "O ID do usuário deve ser um número positivo")
    private Integer usuarioId;

    @NotBlank(message = "O tipo de usuário é obrigatório")
    @Pattern(regexp = "^(assistido|voluntario|assistente_social)$",
            message = "Tipo de usuário deve ser: assistido, voluntario ou assistente_social")
    private String tipoUsuario;

    @NotNull(message = "O arquivo é obrigatório")
    private MultipartFile arquivo;

    private static final long TAMANHO_MAXIMO = 1048576L;
    private static final List<String> TIPOS_PERMITIDOS = Arrays.asList(
            "image/jpeg", "image/jpg", "image/png", "image/gif", "image/webp"
    );

    public FotoUploadInput() {
    }

    public FotoUploadInput(Integer usuarioId, String tipoUsuario, MultipartFile arquivo) {
        this.usuarioId = usuarioId;
        this.tipoUsuario = tipoUsuario;
        this.arquivo = arquivo;
    }

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public MultipartFile getArquivo() {
        return arquivo;
    }

    public void setArquivo(MultipartFile arquivo) {
        this.arquivo = arquivo;
    }

    // Métodos de validação personalizados
    public boolean isArquivoVazio() {
        return arquivo == null || arquivo.isEmpty();
    }

    public boolean isTamanhoValido() {
        return arquivo != null && arquivo.getSize() <= TAMANHO_MAXIMO;
    }

    public boolean isTipoValido() {
        if (arquivo == null) return false;
        String contentType = arquivo.getContentType();
        return contentType != null && TIPOS_PERMITIDOS.contains(contentType.toLowerCase());
    }

    public String getExtensaoArquivo() {
        if (arquivo == null) return "";
        String nomeOriginal = arquivo.getOriginalFilename();
        if (nomeOriginal != null && nomeOriginal.contains(".")) {
            return nomeOriginal.substring(nomeOriginal.lastIndexOf("."));
        }
        return "";
    }

    public long getTamanhoMaximo() {
        return TAMANHO_MAXIMO;
    }

    public List<String> getTiposPermitidos() {
        return TIPOS_PERMITIDOS;
    }
}