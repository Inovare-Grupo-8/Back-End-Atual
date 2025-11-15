package org.com.imaapi.application.useCaseImpl.perfil;

import org.com.imaapi.application.useCase.perfil.AtualizarDadosPessoaisUseCase;
import org.com.imaapi.application.dto.usuario.input.UsuarioInputAtualizacaoDadosPessoais;
import org.com.imaapi.application.dto.usuario.output.UsuarioOutput;
import org.com.imaapi.domain.model.Usuario;
import org.com.imaapi.domain.model.Ficha;
import org.com.imaapi.domain.model.Telefone;
import org.com.imaapi.domain.repository.UsuarioRepository;
import org.com.imaapi.domain.repository.TelefoneRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AtualizarDadosPessoaisUseCaseImpl implements AtualizarDadosPessoaisUseCase {
    private static final Logger LOGGER = LoggerFactory.getLogger(AtualizarDadosPessoaisUseCaseImpl.class);

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private TelefoneRepository telefoneRepository;

    @Override
    public UsuarioOutput atualizarDadosPessoais(Integer usuarioId, UsuarioInputAtualizacaoDadosPessoais dadosPessoais) {
        LOGGER.info("Atualizando dados pessoais para o usuário com ID: {}", usuarioId);
        Usuario usuario = usuarioRepository.findById(usuarioId).orElse(null);
        if (usuario == null) {
            LOGGER.warn("Usuário não encontrado para o ID: {}", usuarioId);
            return null;
        }

        Ficha ficha = usuario.getFicha();
        if (ficha == null) {
            LOGGER.warn("Ficha não encontrada para o usuário com ID: {}", usuarioId);
            return null;
        }

        // Atualizar dados básicos da ficha
        if (dadosPessoais.getNome() != null) {
            ficha.setNome(dadosPessoais.getNome());
        }
        if (dadosPessoais.getSobrenome() != null) {
            ficha.setSobrenome(dadosPessoais.getSobrenome());
        }
        if (dadosPessoais.getDataNascimento() != null) {
            ficha.setDtNascim(dadosPessoais.getDataNascimento());
        }
        
        // Atualizar gênero se fornecido
        if (dadosPessoais.getGenero() != null) {
            ficha.setGenero(dadosPessoais.getGenero());
            LOGGER.info("Gênero atualizado para: {}", dadosPessoais.getGenero());
        }

        // Atualizar email do usuário
        if (dadosPessoais.getEmail() != null && !dadosPessoais.getEmail().trim().isEmpty()) {
            usuario.setEmail(dadosPessoais.getEmail());
            LOGGER.info("Email atualizado para: {}", dadosPessoais.getEmail());
        }

        // Atualizar telefone se fornecido
        if (dadosPessoais.getTelefone() != null && !dadosPessoais.getTelefone().trim().isEmpty()) {
            atualizarTelefone(ficha, dadosPessoais.getTelefone());
        }

        usuarioRepository.save(usuario);
        LOGGER.info("Dados pessoais atualizados com sucesso para o usuário com ID: {}", usuarioId);

        return new UsuarioOutput(
                ficha.getNome(),
                ficha.getCpf(),
                usuario.getEmail(),
                ficha.getDtNascim(),
                usuario.getTipo()
        );
    }

    private void atualizarTelefone(Ficha ficha, String telefoneString) {
        List<Telefone> telefones = telefoneRepository.findByFichaIdFicha(ficha.getIdFicha());
        Telefone telefone;

        if (telefones.isEmpty()) {
            telefone = new Telefone();
            telefone.setFicha(ficha);
            LOGGER.info("Criando novo telefone para ficha ID: {}", ficha.getIdFicha());
        } else {
            telefone = telefones.get(0);
            LOGGER.info("Atualizando telefone existente ID: {}", telefone.getIdTelefone());
        }

        // Parse do telefone formato "(11) 98765-4321" ou "(11) 9876-5432"
        String telefoneCompleto = telefoneString.replaceAll("[()\\s-]", "");

        if (telefoneCompleto.length() >= 10) {
            String ddd = telefoneCompleto.substring(0, 2);
            String numero = telefoneCompleto.substring(2);

            telefone.setDdd(ddd);

            // Dividir número em prefixo e sufixo
            if (numero.length() == 9) { // Celular com 9 dígitos
                telefone.setPrefixo(numero.substring(0, 5));
                telefone.setSufixo(numero.substring(5));
            } else if (numero.length() == 8) { // Fixo com 8 dígitos
                telefone.setPrefixo(numero.substring(0, 4));
                telefone.setSufixo(numero.substring(4));
            } else {
                // Fallback para outros tamanhos
                int splitPoint = numero.length() - 4;
                telefone.setPrefixo(numero.substring(0, splitPoint));
                telefone.setSufixo(numero.substring(splitPoint));
            }

            if (telefone.getWhatsapp() == null) {
                telefone.setWhatsapp(true); // Default para WhatsApp
            }

            telefoneRepository.save(telefone);
            LOGGER.info("Telefone atualizado: DDD={}, Prefixo={}, Sufixo={}", telefone.getDdd(), telefone.getPrefixo(), telefone.getSufixo());
        } else {
            LOGGER.warn("Telefone inválido fornecido: {}", telefoneString);
        }
    }
}