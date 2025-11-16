package org.com.imaapi.application.useCaseImpl.usuario;

import org.com.imaapi.application.dto.usuario.input.AssistenteSocialInput;
import org.com.imaapi.application.dto.usuario.output.AssistenteSocialOutput;
import org.com.imaapi.application.useCase.usuario.AtualizarAssistenteSocialUseCase;
import org.com.imaapi.domain.model.Ficha;
import org.com.imaapi.domain.model.Usuario;
import org.com.imaapi.domain.model.Voluntario;
import org.com.imaapi.domain.model.enums.Genero;
import org.com.imaapi.domain.repository.UsuarioRepository;
import org.com.imaapi.domain.repository.VoluntarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class AtualizarAssistenteSocialUseCaseImpl implements AtualizarAssistenteSocialUseCase {
    private static final Logger LOGGER = LoggerFactory.getLogger(AtualizarAssistenteSocialUseCaseImpl.class);

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private VoluntarioRepository voluntarioRepository;

    @Override
    @Transactional
    public AssistenteSocialOutput executar(Integer usuarioId, AssistenteSocialInput input) {
        LOGGER.info("Atualizando assistente social com ID: {}", usuarioId);
        
        Usuario usuario = usuarioRepository.findById(usuarioId).orElse(null);
        if (usuario == null) {
            LOGGER.warn("Usuário não encontrado para o ID: {}", usuarioId);
            return null;
        }
        
        // Verificar se o usuário é um assistente social
        Voluntario voluntario = voluntarioRepository.findByUsuario_IdUsuario(usuarioId);
        if (voluntario == null || !voluntario.getFuncao().equals("ASSISTENCIA_SOCIAL")) {
            LOGGER.warn("Usuário com ID {} não é um assistente social", usuarioId);
            return null;
        }
        
        // Atualizar dados do usuário
        Ficha ficha = usuario.getFicha();
        if (ficha == null) {
            ficha = new Ficha();
            usuario.setFicha(ficha);
        }
        
        // Atualizar dados pessoais
        if (input.getNome() != null) ficha.setNome(input.getNome());
        if (input.getSobrenome() != null) ficha.setSobrenome(input.getSobrenome());
        if (input.getCpf() != null) ficha.setCpf(input.getCpf());
        
        // Converter String para LocalDate
        if (input.getDataNascimento() != null && !input.getDataNascimento().isEmpty()) {
            try {
                LocalDate dataNascimento = LocalDate.parse(input.getDataNascimento(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                ficha.setDtNascim(dataNascimento);
            } catch (Exception e) {
                LOGGER.error("Erro ao converter data de nascimento: {}", e.getMessage());
            }
        }
        
        // Converter String para Genero
        if (input.getGenero() != null) {
            try {
                ficha.setGenero(Genero.valueOf(input.getGenero().toUpperCase()));
            } catch (IllegalArgumentException e) {
                LOGGER.error("Valor de gênero inválido: {}", input.getGenero());
            }
        }
        
        // Atualizar dados profissionais
        if (input.getBio() != null) voluntario.setBiografiaProfissional(input.getBio());
        if (input.getCrp() != null) voluntario.setRegistroProfissional(input.getCrp());
        
        // Salvar alterações
        usuarioRepository.save(usuario);
        voluntarioRepository.save(voluntario);
        
        // Criar output
        AssistenteSocialOutput output = new AssistenteSocialOutput();
        output.setIdUsuario(usuario.getIdUsuario());
        output.setNome(ficha.getNome());
        output.setSobrenome(ficha.getSobrenome());
        output.setEmail(usuario.getEmail());
        
        LOGGER.info("Assistente social atualizado com sucesso. ID: {}", usuarioId);
        return output;
    }
}