package org.com.imaapi.application.usecase;

import org.com.imaapi.application.useCase.perfil.*;
import org.com.imaapi.application.dto.usuario.input.UsuarioInputAtualizacaoDadosPessoais;
import org.com.imaapi.application.dto.usuario.input.VoluntarioDadosProfissionaisInput;
import org.com.imaapi.application.dto.usuario.output.UsuarioDadosPessoaisOutput;
import org.com.imaapi.application.dto.usuario.output.UsuarioOutput;
import org.com.imaapi.application.dto.usuario.output.EnderecoOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
@Transactional
public class PerfilService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PerfilService.class);

    @Autowired
    private BuscarDadosPessoaisUseCase buscarDadosPessoaisUseCase;
    
    @Autowired
    private AtualizarDadosPessoaisUseCase atualizarDadosPessoaisUseCase;
    
    @Autowired
    private AtualizarDadosPessoaisCompletoUseCase atualizarDadosPessoaisCompletoUseCase;
    
    @Autowired
    private BuscarEnderecoUseCase buscarEnderecoUseCase;
    
    @Autowired
    private AtualizarEnderecoUseCase atualizarEnderecoUseCase;
    
    @Autowired
    private SalvarFotoUseCase salvarFotoUseCase;
    
    @Autowired
    private AtualizarDadosProfissionaisUseCase atualizarDadosProfissionaisUseCase;
    
    @Autowired
    private CriarDisponibilidadeUseCase criarDisponibilidadeUseCase;
    
    @Autowired
    private AtualizarDisponibilidadeUseCase atualizarDisponibilidadeUseCase;

    public UsuarioDadosPessoaisOutput buscarDadosPessoaisPorId(Integer usuarioId) {
        return buscarDadosPessoaisUseCase.buscarDadosPessoaisPorId(usuarioId);
    }

    public UsuarioOutput atualizarDadosPessoais(Integer usuarioId, UsuarioInputAtualizacaoDadosPessoais dadosPessoais) {
        return atualizarDadosPessoaisUseCase.atualizarDadosPessoais(usuarioId, dadosPessoais);
    }

    public UsuarioDadosPessoaisOutput atualizarDadosPessoaisCompleto(Integer usuarioId, UsuarioInputAtualizacaoDadosPessoais dadosPessoais) {
        return atualizarDadosPessoaisCompletoUseCase.atualizarDadosPessoaisCompleto(usuarioId, dadosPessoais);
    }

    public EnderecoOutput buscarEnderecoPorId(Integer usuarioId) {
        return buscarEnderecoUseCase.buscarEnderecoPorId(usuarioId);
    }

    public boolean atualizarEnderecoPorUsuarioId(Integer usuarioId, String cep, String numero, String complemento) {
        return atualizarEnderecoUseCase.atualizarEnderecoPorUsuarioId(usuarioId, cep, numero, complemento);
    }

    public String salvarFoto(Integer usuarioId, String tipo, MultipartFile file) throws IOException {
        return salvarFotoUseCase.salvarFoto(usuarioId, tipo, file);
    }

    public boolean atualizarDadosProfissionais(Integer usuarioId, VoluntarioDadosProfissionaisInput dadosProfissionais) {
        return atualizarDadosProfissionaisUseCase.atualizarDadosProfissionais(usuarioId, dadosProfissionais);
    }

    public boolean criarDisponibilidade(Integer usuarioId, Map<String, Object> disponibilidade) {
        return criarDisponibilidadeUseCase.criarDisponibilidade(usuarioId, disponibilidade);
    }

    public boolean atualizarDisponibilidade(Integer usuarioId, Map<String, Object> disponibilidade) {
        return atualizarDisponibilidadeUseCase.atualizarDisponibilidade(usuarioId, disponibilidade);
    }
}