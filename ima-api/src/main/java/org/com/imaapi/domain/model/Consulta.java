package org.com.imaapi.domain.model;

import org.com.imaapi.domain.model.enums.ModalidadeConsulta;
import org.com.imaapi.domain.model.enums.StatusConsulta;
import org.com.imaapi.domain.model.Especialidade;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "consulta")
public class Consulta implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_consulta")
    private Integer idConsulta;

    @Column(name = "horario", nullable = false)
    private LocalDateTime horario;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private StatusConsulta status;

    @Enumerated(EnumType.STRING)
    @Column(name = "modalidade", nullable = false, length = 15)
    private ModalidadeConsulta modalidade;

    @Column(name = "local", length = 45)
    private String local;
    
    @Column(name = "observacoes", length = 255)
    private String observacoes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_especialidade", nullable = false)
    private Especialidade especialidade;

    @ManyToOne(fetch = FetchType.LAZY) 
    @JoinColumn(name = "fk_cliente", nullable = false)
    private Usuario assistido;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_especialista", nullable = false) 
    private Usuario voluntario;

    @Column(name = "feedback_status", length = 20)
    private String feedbackStatus = "PENDENTE";
    
    @Column(name = "avaliacao_status", length = 20)
    private String avaliacaoStatus = "PENDENTE";

    @CreationTimestamp
    @Column(name = "criado_em", updatable = false)
    private LocalDateTime criadoEm;

    @UpdateTimestamp
    @Column(name = "atualizado_em")
    private LocalDateTime atualizadoEm;

    @Column(name = "versao")
    @Version
    private Integer versao = 0;

    public Consulta() {}

    public Consulta(Integer idConsulta, LocalDateTime horario, StatusConsulta status, ModalidadeConsulta modalidade,
                    String local, String observacoes, Especialidade especialidade, Usuario assistido,
                    Usuario voluntario, String feedbackStatus, String avaliacaoStatus) {
        this.idConsulta = idConsulta;
        this.horario = horario;
        this.status = status;
        this.modalidade = modalidade;
        this.local = local;
        this.observacoes = observacoes;
        this.especialidade = especialidade;
        this.assistido = assistido;
        this.voluntario = voluntario;
        this.feedbackStatus = feedbackStatus;
        this.avaliacaoStatus = avaliacaoStatus;
    }

    public Integer getIdConsulta() {
        return idConsulta;
    }

    public void setIdConsulta(Integer idConsulta) {
        this.idConsulta = idConsulta;
    }

    public LocalDateTime getHorario() {
        return horario;
    }

    public void setHorario(LocalDateTime horario) {
        this.horario = horario;
    }

    public StatusConsulta getStatus() {
        return status;
    }

    public void setStatus(StatusConsulta status) {
        this.status = status;
    }

    public ModalidadeConsulta getModalidade() {
        return modalidade;
    }

    public void setModalidade(ModalidadeConsulta modalidade) {
        this.modalidade = modalidade;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public Especialidade getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(Especialidade especialidade) {
        this.especialidade = especialidade;
    }

    public Usuario getAssistido() {
        return assistido;
    }

    public void setAssistido(Usuario assistido) {
        this.assistido = assistido;
    }

    public Usuario getVoluntario() {
        return voluntario;
    }

    public void setVoluntario(Usuario voluntario) {
        this.voluntario = voluntario;
    }

    public String getFeedbackStatus() {
        return feedbackStatus;
    }

    public void setFeedbackStatus(String feedbackStatus) {
        this.feedbackStatus = feedbackStatus;
    }

    public String getAvaliacaoStatus() {
        return avaliacaoStatus;
    }

    public void setAvaliacaoStatus(String avaliacaoStatus) {
        this.avaliacaoStatus = avaliacaoStatus;
    }

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }

    public void setCriadoEm(LocalDateTime criadoEm) {
        this.criadoEm = criadoEm;
    }

    public LocalDateTime getAtualizadoEm() {
        return atualizadoEm;
    }

    public void setAtualizadoEm(LocalDateTime atualizadoEm) {
        this.atualizadoEm = atualizadoEm;
    }

    public Integer getVersao() {
        return versao;
    }

    public void setVersao(Integer versao) {
        this.versao = versao;
    }
}