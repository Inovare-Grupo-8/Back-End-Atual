package org.com.imaapi.application.useCaseImpl;

public interface UsuarioServiceImpl {
    Usuario cadastrarPrimeiraFase(UsuarioInputPrimeiraFase usuarioInputPrimeiraFase);
    
    Usuario cadastrarPrimeiraFaseVoluntario(UsuarioInputPrimeiraFase usuarioInputPrimeiraFase);    Usuario cadastrarSegundaFase(Integer idUsuario, UsuarioInputSegundaFase usuarioInputSegundaFase);
    
    Usuario cadastrarSegundaFaseVoluntario(Integer idUsuario, UsuarioInputSegundaFase usuarioInputSegundaFase);

    UsuarioTokenOutput autenticar(Usuario usuario, Ficha ficha, String senha);

    List<UsuarioListarOutput> buscarUsuarios();

    Optional<Usuario> buscaUsuario(Integer id);

    public Optional<Usuario> buscaUsuarioPorNome(String nome);

    void cadastrarUsuarioOAuth(OAuth2User usuario);

    Optional<Usuario> buscaUsuarioPorEmail(String email);

    UsuarioListarOutput atualizarUsuario(Integer id, UsuarioInputSegundaFase usuarioInputSegundaFase);

    void deletarUsuario(Integer id);
      
    Usuario buscarDadosPrimeiraFase(Integer idUsuario);    Usuario buscarDadosPrimeiraFase(String email);

    String enviarCredenciaisVoluntario(String email, String nome, String senha);

    List<UsuarioClassificacaoOutput> buscarUsuariosNaoClassificados();    UsuarioListarOutput classificarUsuarioComoGratuidade(Integer id);

    UsuarioListarOutput classificarUsuarioComoValorSocial(Integer id);
    
    void atualizarUltimoAcesso(Integer idUsuario);

    List<VoluntarioListagemOutput> listarVoluntarios();
}