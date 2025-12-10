package org.com.imaapi.application.useCase.perfil;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface SalvarFotoUseCase {
    String salvarFoto(Integer usuarioId, String tipo, MultipartFile file) throws IOException;
}
