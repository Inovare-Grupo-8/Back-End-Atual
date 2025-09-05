package org.com.imaapi.util.service;

import org.com.imaapi.domain.model.usuario.Ficha;
import org.com.imaapi.domain.model.usuario.Usuario;
import org.com.imaapi.domain.model.usuario.usuarioInputDTO.UsuarioInputPrimeiraFaseDTO;
import org.com.imaapi.domain.model.usuario.usuarioInputDTO.UsuarioInputSegundaFaseDTO;
import org.com.imaapi.domain.model.usuario.UsuarioOutputDTO.UsuarioListarOutputDTO;
import org.com.imaapi.domain.model.usuario.UsuarioOutputDTO.UsuarioTokenOutputDTO;
import org.com.imaapi.domain.model.usuario.UsuarioOutputDTO.UsuarioClassificacaoOutputDTO;
import org.com.imaapi.domain.model.usuario.UsuarioOutputDTO.VoluntarioListagemOutputDTO;

import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {
    Usuario cadastrarPrimeiraFase(UsuarioInputPrimeiraFaseDTO usuarioInputPrimeiraFase);
    
    Usuario cadastrarPrimeiraFaseVoluntario(UsuarioInputPrimeiraFaseDTO usuarioInputPrimeiraFase);    Usuario cadastrarSegundaFase(Integer idUsuario, UsuarioInputSegundaFaseDTO usuarioInputSegundaFase);
    
    Usuario cadastrarSegundaFaseVoluntario(Integer idUsuario, UsuarioInputSegundaFaseDTO usuarioInputSegundaFase);

    UsuarioTokenOutputDTO autenticar(Usuario usuario, Ficha ficha, String senha);

    List<UsuarioListarOutputDTO> buscarUsuarios();

    Optional<Usuario> buscaUsuario(Integer id);

    public Optional<Usuario> buscaUsuarioPorNome(String nome);

    void cadastrarUsuarioOAuth(OAuth2User usuario);

    Optional<Usuario> buscaUsuarioPorEmail(String email);

    UsuarioListarOutputDTO atualizarUsuario(Integer id, UsuarioInputSegundaFaseDTO usuarioInputSegundaFase);

    void deletarUsuario(Integer id);
      
    Usuario buscarDadosPrimeiraFase(Integer idUsuario);    Usuario buscarDadosPrimeiraFase(String email);

    String enviarCredenciaisVoluntario(String email, String nome, String senha);

    List<UsuarioClassificacaoOutputDTO> buscarUsuariosNaoClassificados();    UsuarioListarOutputDTO classificarUsuarioComoGratuidade(Integer id);

    UsuarioListarOutputDTO classificarUsuarioComoValorSocial(Integer id);
    
    void atualizarUltimoAcesso(Integer idUsuario);

    List<VoluntarioListagemOutputDTO> listarVoluntarios();
}