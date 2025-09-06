package org.com.imaapi.application.dto.consulta.input;

import jakarta.persistence.Id;
import org.com.imaapi.domain.model.enums.ModalidadeConsulta;
import org.com.imaapi.domain.model.enums.StatusConsulta;
import org.com.imaapi.domain.model.especialidade.Especialidade;
import org.com.imaapi.domain.model.Usuario;

import java.time.LocalDateTime;

public class ConsultaDetailDTO {
    @Id
    private Integer id;
    private LocalDateTime horario;
    private StatusConsulta status;
    private ModalidadeConsulta modalidade;
    private String local;
    private String observacoes;
    private Integer especialidadeId;
    private String especialidadeNome;
    private Integer assistidoId;
    private String assistidoNome;
    private String assistidoEmail;
    private Integer voluntarioId;
    private String voluntarioNome;
    private String voluntarioEmail;

    public ConsultaDetailDTO() {
    }


    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public LocalDateTime getHorario() { return horario; }
    public void setHorario(LocalDateTime horario) { this.horario = horario; }

    public StatusConsulta getStatus() { return status; }
    public void setStatus(StatusConsulta status) { this.status = status; }

    public ModalidadeConsulta getModalidade() { return modalidade; }
    public void setModalidade(ModalidadeConsulta modalidade) { this.modalidade = modalidade; }

    public String getLocal() { return local; }
    public void setLocal(String local) { this.local = local; }

    public String getObservacoes() { return observacoes; }
    public void setObservacoes(String observacoes) { this.observacoes = observacoes; }

    public Integer getEspecialidadeId() { return especialidadeId; }
    public void setEspecialidadeId(Integer especialidadeId) { this.especialidadeId = especialidadeId; }

    public String getEspecialidadeNome() { return especialidadeNome; }
    public void setEspecialidadeNome(String especialidadeNome) { this.especialidadeNome = especialidadeNome; }

    public Integer getAssistidoId() { return assistidoId; }
    public void setAssistidoId(Integer assistidoId) { this.assistidoId = assistidoId; }

    public String getAssistidoNome() { return assistidoNome; }
    public void setAssistidoNome(String assistidoNome) { this.assistidoNome = assistidoNome; }

    public String getAssistidoEmail() { return assistidoEmail; }
    public void setAssistidoEmail(String assistidoEmail) { this.assistidoEmail = assistidoEmail; }

    public Integer getVoluntarioId() { return voluntarioId; }
    public void setVoluntarioId(Integer voluntarioId) { this.voluntarioId = voluntarioId; }

    public String getVoluntarioNome() { return voluntarioNome; }
    public void setVoluntarioNome(String voluntarioNome) { this.voluntarioNome = voluntarioNome; }

    public String getVoluntarioEmail() { return voluntarioEmail; }
    public void setVoluntarioEmail(String voluntarioEmail) { this.voluntarioEmail = voluntarioEmail; }

    public static ConsultaDetailDTO fromEntities(
            LocalDateTime horario,
            StatusConsulta status,
            ModalidadeConsulta modalidade,
            String local,
            String observacoes,
            Especialidade especialidade,
            Usuario assistido,
            Usuario voluntario) {

        ConsultaDetailDTO dto = new ConsultaDetailDTO();
        dto.setHorario(horario);
        dto.setStatus(status);
        dto.setModalidade(modalidade);
        dto.setLocal(local);
        dto.setObservacoes(observacoes);

        if (especialidade != null) {
            dto.setEspecialidadeId(especialidade.getId());
            dto.setEspecialidadeNome(especialidade.getNome());
        }

        if (assistido != null) {
            dto.setAssistidoId(assistido.getIdUsuario());
            dto.setAssistidoEmail(assistido.getEmail());
            if (assistido.getFicha() != null) {
                dto.setAssistidoNome(assistido.getFicha().getNome());
            }
        }

        if (voluntario != null) {
            dto.setVoluntarioId(voluntario.getIdUsuario());
            dto.setVoluntarioEmail(voluntario.getEmail());
            if (voluntario.getFicha() != null) {
                dto.setVoluntarioNome(voluntario.getFicha().getNome());
            }
        }

        return dto;
    }
}