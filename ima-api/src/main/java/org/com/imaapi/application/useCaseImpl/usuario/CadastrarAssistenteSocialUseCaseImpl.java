package org.com.imaapi.application.useCaseImpl.usuario;

import org.com.imaapi.application.dto.usuario.input.AssistenteSocialInput;
import org.com.imaapi.application.dto.usuario.output.AssistenteSocialOutput;
import org.com.imaapi.application.useCase.usuario.CadastrarAssistenteSocialUseCase;
import org.com.imaapi.domain.model.Ficha;
import org.com.imaapi.domain.model.Usuario;
import org.com.imaapi.domain.model.Voluntario;
import org.com.imaapi.domain.model.enums.Funcao;
import org.com.imaapi.domain.model.enums.Genero;
import org.com.imaapi.domain.model.enums.TipoUsuario;
import org.com.imaapi.domain.repository.FichaRepository;
import org.com.imaapi.domain.repository.UsuarioRepository;
import org.com.imaapi.domain.repository.VoluntarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class CadastrarAssistenteSocialUseCaseImpl implements CadastrarAssistenteSocialUseCase {
    private static final Logger LOGGER = LoggerFactory.getLogger(CadastrarAssistenteSocialUseCaseImpl.class);

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private VoluntarioRepository voluntarioRepository;
    
    @Autowired
    private FichaRepository fichaRepository;
    
//    @Autowired
//    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public AssistenteSocialOutput executar(AssistenteSocialInput input) {
        LOGGER.info("Iniciando cadastro de assistente social com email: {}", input.getEmail());
        
        // Verificar se o email já existe
        if (usuarioRepository.findByEmail(input.getEmail()).isPresent()) {
            LOGGER.warn("Email já cadastrado: {}", input.getEmail());
            throw new IllegalArgumentException("Email já cadastrado");
        }
        
        // Criar usuário
        Usuario usuario = new Usuario();
        usuario.setEmail(input.getEmail());
        usuario.setSenha(input.getSenha());
        usuario.setTipo(TipoUsuario.VOLUNTARIO);
        usuario.setDataCadastro(LocalDate.now());
        usuario.setUltimoAcesso(LocalDateTime.now());
        
        // Criar ficha
        Ficha ficha = new Ficha();
        ficha.setNome(input.getNome());
        ficha.setSobrenome(input.getSobrenome());
        ficha.setCpf(input.getCpf());
        
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
        
        // Salvar ficha primeiro (para evitar erro de entidade transiente)
        Ficha fichaSalva = fichaRepository.save(ficha);
        
        usuario.setFicha(fichaSalva);
        
        // Salvar usuário
        Usuario usuarioSalvo = usuarioRepository.save(usuario);
        
        // Criar voluntário (assistente social)
        Voluntario voluntario = new Voluntario();
        voluntario.setUsuario(usuarioSalvo);
        voluntario.setFuncao(Funcao.ASSISTENCIA_SOCIAL);
        voluntario.setFkUsuario(usuarioSalvo.getIdUsuario());
        voluntario.setDataCadastro(LocalDate.now());
        voluntario.setBiografiaProfissional(input.getBio());
        voluntario.setRegistroProfissional(input.getCrp());
        
        // Salvar voluntário
        Voluntario voluntarioSalvo = voluntarioRepository.save(voluntario);
        
        // Criar output
        AssistenteSocialOutput output = new AssistenteSocialOutput();
        output.setIdUsuario(usuarioSalvo.getIdUsuario());
        output.setNome(fichaSalva.getNome());
        output.setSobrenome(fichaSalva.getSobrenome());
        output.setEmail(usuarioSalvo.getEmail());
        
        LOGGER.info("Assistente social cadastrado com sucesso. ID: {}", usuarioSalvo.getIdUsuario());
        return output;
    }
}