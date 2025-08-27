package org.com.imaapi.model.consulta.mapper;

import org.com.imaapi.model.consulta.Consulta;
import org.com.imaapi.model.consulta.dto.ConsultaDto;
import org.com.imaapi.model.consulta.input.ConsultaInput;
import org.com.imaapi.model.consulta.output.ConsultaOutput;
import org.com.imaapi.model.enums.ModalidadeConsulta;
import org.com.imaapi.model.enums.StatusConsulta;
import org.com.imaapi.model.especialidade.Especialidade;
import org.com.imaapi.model.usuario.Usuario;

public class ConsultaMapper {

    public static ConsultaDto toDto(Consulta consulta) {
        ConsultaDto dto = new ConsultaDto();
        dto.setIdConsulta(consulta.getIdConsulta());
        dto.setHorario(consulta.getHorario());
        dto.setStatus(consulta.getStatus().name());
        dto.setModalidade(consulta.getModalidade().name());
        dto.setLocal(consulta.getLocal());
        dto.setObservacoes(consulta.getObservacoes());

        // Especialidade como objeto
        dto.setEspecialidade(consulta.getEspecialidade());

        // Voluntário como objeto
        dto.setVoluntario(consulta.getVoluntario());

        // Assistido como objeto
        dto.setAssistido(consulta.getAssistido());

        dto.setFeedbackStatus(consulta.getFeedbackStatus());
        dto.setAvaliacaoStatus(consulta.getAvaliacaoStatus());

        return dto;
    }

    public static Consulta toEntity(ConsultaInput input) {
        if (input == null) {
            throw new IllegalArgumentException("O objeto ConsultaInput não pode ser nulo");
        }

        Consulta consulta = new Consulta();

        if (input.getHorario() == null) {
            throw new IllegalArgumentException("O horário da consulta é obrigatório");
        }
        consulta.setHorario(input.getHorario());

        if (input.getStatus() == null) {
            throw new IllegalArgumentException("O status da consulta é obrigatório");
        }
        consulta.setStatus(input.getStatus());

        if (input.getModalidade() == null) {
            throw new IllegalArgumentException("A modalidade da consulta é obrigatória");
        }
        consulta.setModalidade(input.getModalidade());

        if (input.getLocal() == null || input.getLocal().trim().isEmpty()) {
            throw new IllegalArgumentException("O local da consulta é obrigatório");
        }
        consulta.setLocal(input.getLocal());

        consulta.setObservacoes(input.getObservacoes());

        // Especialidade como objeto completo
        if (input.getEspecialidade() != null && input.getEspecialidade().getId() != null) {
            consulta.setEspecialidade(input.getEspecialidade());
        }

        // Assistido como objeto completo
        if (input.getAssistido() != null && input.getAssistido().getIdUsuario() != null) {
            consulta.setAssistido(input.getAssistido());
        }

        // Voluntário como objeto completo
        if (input.getVoluntario() != null && input.getVoluntario().getIdUsuario() != null) {
            consulta.setVoluntario(input.getVoluntario());
        }

        return consulta;
    }

    public static ConsultaOutput toOutput(ConsultaDto dto) {
        ConsultaOutput output = new ConsultaOutput();
        output.setHorario(dto.getHorario());
        output.setStatus(StatusConsulta.valueOf(dto.getStatus()));
        output.setModalidade(ModalidadeConsulta.valueOf(dto.getModalidade()));
        output.setLocal(dto.getLocal());
        output.setObservacoes(dto.getObservacoes());

        // Especialidade como objeto
        output.setEspecialidade(dto.getEspecialidade());

        // Assistido como objeto
        output.setAssistido(dto.getAssistido());

        // Voluntário como objeto
        output.setVoluntario(dto.getVoluntario());

        return output;
    }
}