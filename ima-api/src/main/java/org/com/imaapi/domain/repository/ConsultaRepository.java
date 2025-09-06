package org.com.imaapi.domain.repository;

import org.com.imaapi.application.usecase.consulta.Consulta;
import org.com.imaapi.domain.model.enums.StatusConsulta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ConsultaRepository extends JpaRepository<Consulta, Integer> {
    List<Consulta> findByVoluntario_IdUsuarioAndHorarioBetween(Integer idUsuario, LocalDateTime inicio, LocalDateTime fim);

    List<Consulta> findByAssistido_IdUsuarioAndHorarioBetween(Integer idUsuario, LocalDateTime inicio, LocalDateTime fim);

    List<Consulta> findByVoluntario_IdUsuarioAndStatusOrderByHorarioDesc(Integer idUsuario, String status);

    List<Consulta> findByAssistido_IdUsuarioAndStatusOrderByHorarioDesc(Integer idUsuario, String status);

    List<Consulta> findByVoluntario_IdUsuario(Integer idUsuario);

    List<Consulta> findByAssistido_IdUsuario(Integer idUsuario);

    List<Consulta> findByVoluntario_IdUsuarioAndStatusIn(Integer idUsuario, List<StatusConsulta> statusList);

    List<Consulta> findByAssistido_IdUsuarioAndStatusIn(Integer idUsuario, List<StatusConsulta> statusList);

    List<Consulta> findByVoluntario_IdUsuarioAndHorarioBetweenAndStatusIn(
            Integer idUsuario, LocalDateTime inicio, LocalDateTime fim, List<StatusConsulta> statusList);

    List<Consulta> findByAssistido_IdUsuarioAndHorarioBetweenAndStatusIn(
            Integer idUsuario, LocalDateTime inicio, LocalDateTime fim, List<StatusConsulta> statusList);

    List<Consulta> findByAssistido_IdUsuarioAndHorarioAfterOrderByHorarioAsc(Integer idUsuario, LocalDateTime horario);

    List<Consulta> findByVoluntario_IdUsuarioAndHorarioAfterOrderByHorarioAsc(Integer idUsuario, LocalDateTime horario);

    List<Consulta> findByHorarioBetween(LocalDateTime inicio, LocalDateTime fim);

    List<Consulta> findByAssistido_IdUsuarioOrVoluntario_IdUsuario(Integer idAssistido, Integer idVoluntario);

}
