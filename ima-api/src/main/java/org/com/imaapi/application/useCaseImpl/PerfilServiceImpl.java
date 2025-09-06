package org.com.imaapi.application.useCaseImpl;

public interface PerfilServiceImpl {
    // Métodos genéricos para buscar e atualizar dados
    UsuarioDadosPessoaisOutput buscarDadosPessoaisPorId(Integer usuarioId);
    EnderecoOutput buscarEnderecoPorId(Integer usuarioId);
    UsuarioOutput atualizarDadosPessoais(Integer usuarioId, UsuarioInputAtualizacaoDadosPessoais usuarioInputAtualizacaoDadosPessoais);
    UsuarioDadosPessoaisOutput atualizarDadosPessoaisCompleto(Integer usuarioId, UsuarioInputAtualizacaoDadosPessoais usuarioInputAtualizacaoDadosPessoais);
    boolean atualizarEnderecoPorUsuarioId(Integer usuarioId, String cep, String numero, String complemento);
    boolean atualizarDadosProfissionais(Integer usuarioId, VoluntarioDadosProfissionaisInput dadosProfissionais);
    boolean criarDisponibilidade(Integer usuarioId, Map<String, Object> disponibilidade);
    boolean atualizarDisponibilidade(Integer usuarioId, Map<String, Object> disponibilidade);
    String salvarFoto(Integer usuarioId, String tipo, MultipartFile file) throws IOException;

}