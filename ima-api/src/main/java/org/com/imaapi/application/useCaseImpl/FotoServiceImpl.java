package org.com.imaapi.application.useCaseImpl;

public interface FotoServiceImpl {
    String salvarFoto(String tipoUsuario, Integer usuarioId, MultipartFile file) throws IOException;
}
