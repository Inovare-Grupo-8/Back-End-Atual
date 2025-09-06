package org.com.imaapi.domain.model;

import jakarta.persistence.*;
import lombok.Data;
import org.com.imaapi.application.dto.usuario.input.TelefoneInputDTO;

import java.time.LocalDateTime;


@Data
@Entity
@Table(name = "telefone")
public class Telefone {    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_telefone")
    private Integer idTelefone;

    @ManyToOne
    @JoinColumn(name = "fk_ficha", nullable = false)
    private Ficha ficha;

    @Column(name = "ddd", length = 2)
    private String ddd;

    @Column(name = "prefixo", length = 5)
    private String prefixo;

    @Column(name = "sufixo", length = 4)
    private String sufixo;

    @Column(name = "whatsapp")
    private Boolean whatsapp;

    @Column(name = "criado_em")
    private LocalDateTime criadoEm;

    @Column(name = "atualizado_em")
    private LocalDateTime atualizadoEm;

    @Version
    @Column(name = "versao")
    private Integer versao = 0;

    @PrePersist
    public void prePersist() {
        if (this.criadoEm == null) {
            this.criadoEm = LocalDateTime.now();
        }
        if (this.atualizadoEm == null) {
            this.atualizadoEm = LocalDateTime.now();
        }
    }

    @PreUpdate
    public void preUpdate() {
        this.atualizadoEm = LocalDateTime.now();
    }

    public static Telefone of(TelefoneInputDTO telefoneInputDTO, Ficha ficha) {
        Telefone telefone = new Telefone();
        telefone.setFicha(ficha);
        telefone.setDdd(telefoneInputDTO.getDdd());
        telefone.setPrefixo(telefoneInputDTO.getPrefixo());
        telefone.setSufixo(telefoneInputDTO.getSufixo());
        telefone.setWhatsapp(telefoneInputDTO.getWhatsapp());
        return telefone;
    }
}