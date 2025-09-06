package org.com.imaapi.application.useCaseImpl;

public interface DisponibilidadeServiceImpl {
    boolean criarDisponibilidade(Integer usuarioId, Disponibilidade disponibilidade);
    boolean atualizarDisponibilidade(Integer usuarioId, Disponibilidade disponibilidade);
}
