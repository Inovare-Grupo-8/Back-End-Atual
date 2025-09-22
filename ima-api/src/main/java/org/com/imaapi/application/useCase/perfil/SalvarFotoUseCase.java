package org.com.imaapi.application.useCase.perfil;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface SalvarFotoUseCase {
    public String salvarFoto(String tipoUsuario, Integer usuarioId, MultipartFile file) throws IOException;
}
