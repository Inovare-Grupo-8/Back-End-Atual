package org.com.imaapi.application.useCase.perfil;

import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

public interface FotoUseCase {
    String salvarFoto(String tipo, Integer usuarioId, MultipartFile file) throws IOException;
}