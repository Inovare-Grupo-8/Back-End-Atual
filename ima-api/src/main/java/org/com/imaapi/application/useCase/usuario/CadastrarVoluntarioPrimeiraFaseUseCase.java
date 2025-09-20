package org.com.imaapi.application.useCase.usuario;

import org.com.imaapi.application.dto.usuario.input.UsuarioInputPrimeiraFase;
import org.com.imaapi.application.dto.usuario.output.UsuarioPrimeiraFaseOutput;

public interface CadastrarVoluntarioPrimeiraFaseUseCase {
    UsuarioPrimeiraFaseOutput executar(UsuarioInputPrimeiraFase usuarioInputPrimeiraFase);
}