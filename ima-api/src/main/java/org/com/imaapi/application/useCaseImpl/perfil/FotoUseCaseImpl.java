package org.com.imaapi.application.useCaseImpl.perfil;

import org.com.imaapi.application.useCase.perfil.FotoUseCase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * Implementação simples do caso de uso de salvar foto.
 * Segue princípios da arquitetura limpa: responsabilidade única,
 * injeção de dependência via construtor e pouca lógica de infraestrutura.
 */
@Service
public class FotoUseCaseImpl implements FotoUseCase {
    private static final Logger LOGGER = LoggerFactory.getLogger(FotoUseCaseImpl.class);

    private final Path uploadPath;

    public FotoUseCaseImpl(@Value("${app.upload.dir:uploads}") String uploadsDir) {
        this.uploadPath = Paths.get(uploadsDir).toAbsolutePath().normalize();
    }

    @Override
    public String salvarFoto(String tipo, Integer usuarioId, MultipartFile file) throws IOException {
        Objects.requireNonNull(file, "file must not be null");

        // Garante que o diretório exista; se não puder criar, lança IOException (fail-fast)
        Files.createDirectories(uploadPath);

        String originalFilename = file.getOriginalFilename();
        String fileName = String.format("usuario_%d_%s_%d%s",
                usuarioId, tipo, System.currentTimeMillis(), getFileExtension(originalFilename));

        Path dest = uploadPath.resolve(fileName).toAbsolutePath();

        LOGGER.info("Salvando arquivo em: {}", dest);

        // Transferência para o destino (usa API do MultipartFile)
        file.transferTo(dest.toFile());

        // Retorna caminho relativo para consumo pela camada de API; mantemos o padrão '/uploads/<nome>'
        String dirName = uploadPath.getFileName() != null ? uploadPath.getFileName().toString() : "uploads";
        return "/" + dirName + "/" + fileName;
    }

    private String getFileExtension(String filename) {
        if (filename == null || !filename.contains(".")) return "";
        return filename.substring(filename.lastIndexOf("."));
    }
}
