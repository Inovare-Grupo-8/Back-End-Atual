package org.com.imaapi.application.useCaseImpl.perfil;

import org.com.imaapi.application.useCase.perfil.FotoUseCase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FotoUseCaseImpl implements FotoUseCase {
    
    @Value("${app.upload.dir:uploads}")
    private String uploadsDir;
    
    @Override
    public String salvarFoto(String tipo, Integer usuarioId, MultipartFile file) throws IOException {
        Path uploadPath = Paths.get(uploadsDir).toAbsolutePath();
        File uploadsDirFile = uploadPath.toFile();
        
        if (!uploadsDirFile.exists()) {
            boolean created = uploadsDirFile.mkdirs();
            if (!created) {
                throw new IOException("Não foi possível criar o diretório de uploads: " + uploadPath);
            }
        }
        
        String originalFilename = file.getOriginalFilename();
        String fileName = "usuario_" + usuarioId + "_" + tipo + "_" + System.currentTimeMillis() + getFileExtension(originalFilename);
        File dest = new File(uploadsDirFile, fileName);
        
        System.out.println("Salvando arquivo em: " + dest.getAbsolutePath());
        
        file.transferTo(dest);
        return "/uploads/" + fileName;
    }

    private String getFileExtension(String filename) {
        if (filename == null || !filename.contains(".")) return "";
        return filename.substring(filename.lastIndexOf("."));
    }
}
