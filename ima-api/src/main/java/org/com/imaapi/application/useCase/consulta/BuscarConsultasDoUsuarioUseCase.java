package org.com.imaapi.application.useCase.consulta;

import java.util.List;

public interface BuscarConsultasDoUsuarioUseCase {
    // Executa o caso de uso de busca de consultas do usuário
    List</*ConsultaOutputDTO*/> executar(Integer idUsuario);
}
