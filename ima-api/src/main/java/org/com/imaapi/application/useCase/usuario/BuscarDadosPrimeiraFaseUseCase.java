package org.com.imaapi.application.useCase.usuario;

import org.com.imaapi.application.dto.usuario.output.UsuarioPrimeiraFaseOutput;

public interface BuscarDadosPrimeiraFaseUseCase {
    UsuarioPrimeiraFaseOutput executarPorId(Integer idUsuario);
    UsuarioPrimeiraFaseOutput executarPorEmail(String email);
}