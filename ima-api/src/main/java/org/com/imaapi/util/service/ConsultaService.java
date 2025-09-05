package org.com.imaapi.util.service;

import org.com.imaapi.application.Usecase.Consulta.dto.ConsultaRequestDTO;
import org.com.imaapi.application.Usecase.Consulta.dto.ConsultaRemarcarRequestDTO;
import org.com.imaapi.application.Usecase.Consulta.output.ConsultaOutput;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

import java.time.LocalDate;

public interface ConsultaService {
    ResponseEntity<ConsultaOutput> criarEvento(ConsultaRequestDTO consultaInput);

    ResponseEntity<List<ConsultaDto>> getConsultasDia(String user);

    ResponseEntity<List<ConsultaDto>> getConsultasSemana(String user);

    ResponseEntity<List<ConsultaDto>> getConsultasMes(String user);

    ResponseEntity<Map<String, Object>> getAvaliacoesFeedback(String user);

    ResponseEntity<List<ConsultaDto>> getConsultasRecentes(String user);

    ResponseEntity<ConsultaDto> adicionarFeedback(Integer id, String feedback);

    ResponseEntity<ConsultaDto> adicionarAvaliacao(Integer id, String avaliacao);

    ResponseEntity<ConsultaOutput> getProximaConsulta(Integer idUsuario);

    ResponseEntity<?> getHorariosDisponiveis(LocalDate data, Integer idVoluntario);

    ResponseEntity<List<ConsultaDto>> getTodasConsultas();

    ResponseEntity<List<ConsultaDto>> getConsultasUsuarioLogado();

    public ResponseEntity<ConsultaDto> getConsultaPorId(Integer id);
    
    // Novos métodos
    List<ConsultaOutput> buscarConsultasPorDia(String user, LocalDate data);
    
    List<ConsultaOutput> buscarHistoricoConsultas(String user);
    
    ConsultaOutput buscarConsultaPorIdEUsuario(Integer consultaId, String user);
    
    List<ConsultaOutput> buscarProximasConsultas(String user);
    
    void remarcarConsulta(Integer id, ConsultaRemarcarRequestDTO input);
    
    // Cancel consultation method
    ResponseEntity<ConsultaDto> cancelarConsulta(Integer id);
}