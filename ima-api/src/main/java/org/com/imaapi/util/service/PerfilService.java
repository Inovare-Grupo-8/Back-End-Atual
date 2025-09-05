package org.com.imaapi.util.service;

import org.com.imaapi.domain.model.usuario.usuarioInputDTO.UsuarioInputAtualizacaoDadosPessoaisDTO;
import org.com.imaapi.domain.model.usuario.usuarioInputDTO.VoluntarioDadosProfissionaisInputDTO;
import org.com.imaapi.domain.model.usuario.UsuarioOutputDTO.EnderecoOutputDTO;
import org.com.imaapi.domain.model.usuario.UsuarioOutputDTO.UsuarioDadosPessoaisOutputDTO;
import org.com.imaapi.domain.model.usuario.UsuarioOutputDTO.UsuarioOutputDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

public interface PerfilService {
    // Métodos genéricos para buscar e atualizar dados
    UsuarioDadosPessoaisOutputDTO buscarDadosPessoaisPorId(Integer usuarioId);
    EnderecoOutputDTO buscarEnderecoPorId(Integer usuarioId);
    UsuarioOutputDTO atualizarDadosPessoais(Integer usuarioId, UsuarioInputAtualizacaoDadosPessoaisDTO usuarioInputAtualizacaoDadosPessoais);
    UsuarioDadosPessoaisOutputDTO atualizarDadosPessoaisCompleto(Integer usuarioId, UsuarioInputAtualizacaoDadosPessoaisDTO usuarioInputAtualizacaoDadosPessoais);
    boolean atualizarEnderecoPorUsuarioId(Integer usuarioId, String cep, String numero, String complemento);
    boolean atualizarDadosProfissionais(Integer usuarioId, VoluntarioDadosProfissionaisInputDTO dadosProfissionais);
    boolean criarDisponibilidade(Integer usuarioId, Map<String, Object> disponibilidade);
    boolean atualizarDisponibilidade(Integer usuarioId, Map<String, Object> disponibilidade);
    String salvarFoto(Integer usuarioId, String tipo, MultipartFile file) throws IOException;

}