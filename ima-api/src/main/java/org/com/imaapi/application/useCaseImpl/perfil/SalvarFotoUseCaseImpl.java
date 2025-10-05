package org.com.imaapi.application.useCaseImpl.perfil;

import org.com.imaapi.application.useCase.perfil.SalvarFotoUseCase;
import org.com.imaapi.domain.model.Usuario;
import org.com.imaapi.domain.repository.UsuarioRepository;
import org.com.imaapi.infrastructure.service.FotoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@Transactional
public class SalvarFotoUseCaseImpl implements SalvarFotoUseCase {
    private static final Logger LOGGER = LoggerFactory.getLogger(SalvarFotoUseCaseImpl.class);

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private FotoService fotoService;

    @Override
    public String salvarFoto(Integer usuarioId, String tipo, MultipartFile file) throws IOException {
        LOGGER.info("Salvando foto para o usuário com ID: {}", usuarioId);
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        String fotoUrl = fotoService.salvarFoto(tipo, usuarioId, file);
        usuario.setFotoUrl(fotoUrl);
        usuarioRepository.save(usuario);

        LOGGER.info("Foto salva com sucesso para o usuário com ID: {}", usuarioId);
        return fotoUrl;
    }
}
