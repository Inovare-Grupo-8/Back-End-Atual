package org.com.imaapi.application.useCase.consulta;

import org.com.imaapi.application.dto.consulta.output.ConsultaOutput;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

/**
 * Interface for consultation operations
 */
public interface ConsultaUseCase {
    /**
     * Retrieves consultations for a specific day
     * 
     * @param user The user identifier
     * @param data The date to search for
     * @return List of consultations for the specified day
     */
    List<ConsultaOutput> buscarConsultasPorDia(String user, LocalDate data);
    
    /**
     * Retrieves paginated consultations for a specific day
     * 
     * @param user The user identifier
     * @param data The date to search for
     * @param pageable Pagination information
     * @return Page of consultations for the specified day
     */
    Page<ConsultaOutput> buscarConsultasPorDiaPaginado(String user, LocalDate data, Pageable pageable);
    
    /**
     * Retrieves consultations by specialty
     * 
     * @param especialidadeId The specialty ID
     * @param pageable Pagination information
     * @return Page of consultations for the specified specialty
     */
    Page<ConsultaOutput> buscarConsultasPorEspecialidade(Integer especialidadeId, Pageable pageable);
    
    /**
     * Retrieves consultation statistics
     * 
     * @return List of consultation status counts
     */
    List<ConsultaStatusCount> buscarEstatisticasConsultas();
    
    /**
     * Inner class for consultation status count
     */
    class ConsultaStatusCount {
        private String status;
        private Long count;
        
        public String getStatus() {
            return status;
        }
        
        public void setStatus(String status) {
            this.status = status;
        }
        
        public Long getCount() {
            return count;
        }
        
        public void setCount(Long count) {
            this.count = count;
        }
    }
}