package org.com.imaapi.application.useCase.consulta;

public interface AdicionarFeedbackConsultaUseCase {
    // Executa o caso de uso de adicionar feedback à consulta
    void executar(Integer idConsulta, String feedback);
}
