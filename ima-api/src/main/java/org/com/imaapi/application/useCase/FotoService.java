package org.com.imaapi.application.usecase;


@Service
public class FotoService implements FotoService {

    @Override
    public String salvarFoto(String tipoUsuario, Integer usuarioId, MultipartFile file) throws IOException {
        Path uploadPath = Paths.get("uploads/");
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Usar extensão do arquivo original para manter o tipo
        String originalFilename = file.getOriginalFilename();
        String fileExtension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }

        // Criar nome único para evitar conflitos entre tipos de usuário
        String fileName = String.format("%s_user_%d%s", tipoUsuario, usuarioId, fileExtension);
        Path filePath = uploadPath.resolve(fileName);

        // Remover arquivo anterior se existir (substituição)
        if (Files.exists(filePath)) {
            Files.delete(filePath);
        }

        // Salvar o novo arquivo
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        return "/uploads/" + fileName;
    }
}
