package org.com.imaapi.core.adapter.repositoryImpl;

import org.com.imaapi.model.usuario.Disponibilidade;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface DisponibilidadeServiceImpl {
    boolean criarDisponibilidade(Integer usuarioId, Disponibilidade disponibilidade);
    boolean atualizarDisponibilidade(Integer usuarioId, Disponibilidade disponibilidade);
}
