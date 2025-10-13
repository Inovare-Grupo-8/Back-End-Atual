package org.com.imaapi.application.useCaseImpl.perfil;

import org.com.imaapi.application.useCase.perfil.FotoUseCase;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;

@Service
public class FotoUseCaseImpl implements FotoUseCase {
    @Override
    public String salvarFoto(String tipo, Integer usuarioId, MultipartFile file) throws IOException {
        String uploadsDir = "uploads";
        String originalFilename = file.getOriginalFilename();
        String fileName = "usuario_" + usuarioId + "_" + tipo + "_" + System.currentTimeMillis() + getFileExtension(originalFilename);
        File dest = new File(uploadsDir, fileName);
        file.transferTo(dest);
        return "/uploads/" + fileName;
    }

    private String getFileExtension(String filename) {
        if (filename == null || !filename.contains(".")) return "";
        return filename.substring(filename.lastIndexOf("."));
    }
}
