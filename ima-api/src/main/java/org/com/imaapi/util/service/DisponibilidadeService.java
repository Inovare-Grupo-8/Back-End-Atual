package org.com.imaapi.util.service;

import org.com.imaapi.domain.model.usuario.Disponibilidade;

public interface DisponibilidadeService {
    boolean criarDisponibilidade(Integer usuarioId, Disponibilidade disponibilidade);
    boolean atualizarDisponibilidade(Integer usuarioId, Disponibilidade disponibilidade);
}
