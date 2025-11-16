package org.com.imaapi.application.useCaseImpl.telefone;

import org.com.imaapi.application.dto.usuario.input.TelefoneInput;
import org.com.imaapi.application.useCase.telefone.TelefoneUseCase;
import org.com.imaapi.domain.model.Ficha;
import org.com.imaapi.domain.model.Telefone;
import org.com.imaapi.domain.repository.FichaRepository;
import org.com.imaapi.domain.repository.TelefoneRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TelefoneUseCaseImpl implements TelefoneUseCase {
    private static final Logger LOGGER = LoggerFactory.getLogger(TelefoneUseCaseImpl.class);

    @Autowired
    private TelefoneRepository telefoneRepository;
    
    @Autowired
    private FichaRepository fichaRepository;

    @Override
    public List<Telefone> buscarPorFicha(Integer idFicha) {
        LOGGER.info("Buscando telefones para a ficha com ID: {}", idFicha);
        return telefoneRepository.findByFichaIdFicha(idFicha);
    }

    @Override
    public Optional<Telefone> buscarPorId(Integer idTelefone) {
        LOGGER.info("Buscando telefone com ID: {}", idTelefone);
        return telefoneRepository.findById(idTelefone);
    }

    @Override
    @Transactional
    public Telefone salvar(Integer idFicha, TelefoneInput telefoneInput) {
        LOGGER.info("Salvando novo telefone para a ficha com ID: {}", idFicha);
        
        Optional<Ficha> fichaOptional = fichaRepository.findById(idFicha);
        if (fichaOptional.isEmpty()) {
            LOGGER.error("Ficha não encontrada com ID: {}", idFicha);
            return null;
        }
        
        Ficha ficha = fichaOptional.get();
        Telefone telefone = Telefone.of(telefoneInput, ficha);
        
        return telefoneRepository.save(telefone);
    }

    @Override
    @Transactional
    public Telefone atualizar(Integer idTelefone, TelefoneInput telefoneInput) {
        LOGGER.info("Atualizando telefone com ID: {}", idTelefone);
        
        Optional<Telefone> telefoneOptional = telefoneRepository.findById(idTelefone);
        if (telefoneOptional.isEmpty()) {
            LOGGER.error("Telefone não encontrado com ID: {}", idTelefone);
            return null;
        }
        
        Telefone telefone = telefoneOptional.get();
        telefone.setDdd(telefoneInput.getDdd());
        telefone.setPrefixo(telefoneInput.getPrefixo());
        telefone.setSufixo(telefoneInput.getSufixo());
        telefone.setWhatsapp(telefoneInput.getWhatsapp());
        
        return telefoneRepository.save(telefone);
    }

    @Override
    @Transactional
    public Telefone atualizarPorFicha(Integer idFicha, TelefoneInput telefoneInput) {
        LOGGER.info("Atualizando telefone para a ficha com ID: {}", idFicha);
        
        // Buscar telefones existentes para a ficha
        List<Telefone> telefones = telefoneRepository.findByFichaIdFicha(idFicha);
        
        if (telefones.isEmpty()) {
            LOGGER.error("Nenhum telefone encontrado para a ficha com ID: {}", idFicha);
            return null;
        }
        
        // Atualizar o primeiro telefone encontrado
        Telefone telefone = telefones.get(0);
        telefone.setDdd(telefoneInput.getDdd());
        telefone.setPrefixo(telefoneInput.getPrefixo());
        telefone.setSufixo(telefoneInput.getSufixo());
        telefone.setWhatsapp(telefoneInput.getWhatsapp());
        
        return telefoneRepository.save(telefone);
    }

    @Override
    @Transactional
    public boolean remover(Integer idTelefone) {
        LOGGER.info("Removendo telefone com ID: {}", idTelefone);
        
        if (!telefoneRepository.existsById(idTelefone)) {
            LOGGER.error("Telefone não encontrado com ID: {}", idTelefone);
            return false;
        }
        
        telefoneRepository.deleteById(idTelefone);
        return true;
    }
}