package org.com.imaapi.application.useCase.telefone;

import org.com.imaapi.application.dto.usuario.input.TelefoneInput;
import org.com.imaapi.domain.model.Telefone;

import java.util.List;
import java.util.Optional;

public interface TelefoneUseCase {
    List<Telefone> buscarPorFicha(Integer idFicha);
    Optional<Telefone> buscarPorId(Integer idTelefone);
    Telefone salvar(Integer idFicha, TelefoneInput telefoneInput);
    Telefone atualizar(Integer idTelefone, TelefoneInput telefoneInput);
    boolean remover(Integer idTelefone);
}