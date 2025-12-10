package org.com.imaapi.application.useCase.disponibilidade;

import org.com.imaapi.application.dto.disponibilidade.input.DisponibilidadeInput;
import org.com.imaapi.application.dto.disponibilidade.output.DisponibilidadeOutput;

public interface CriarDisponibilidadeUseCase {
    DisponibilidadeOutput criarDisponibilidade(DisponibilidadeInput disponibilidadeInput);
}