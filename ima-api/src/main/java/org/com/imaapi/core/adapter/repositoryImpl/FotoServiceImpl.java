package org.com.imaapi.core.adapter.repositoryImpl;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FotoServiceImpl {
    String salvarFoto(String tipoUsuario, Integer usuarioId, MultipartFile file) throws IOException;
}
