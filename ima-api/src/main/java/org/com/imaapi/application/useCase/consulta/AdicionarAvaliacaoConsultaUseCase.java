package org.com.imaapi.application.useCase.consulta;

public interface AdicionarAvaliacaoConsultaUseCase {
    // Executa o caso de uso de adicionar avaliação à consulta
    void executar(Integer idConsulta, Integer nota);
}
