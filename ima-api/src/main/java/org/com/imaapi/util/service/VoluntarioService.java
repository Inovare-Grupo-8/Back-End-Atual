package org.com.imaapi.util.service;

import org.com.imaapi.domain.model.usuario.usuarioInputDTO.VoluntarioInputDTO;

public interface VoluntarioService {
    public void cadastrarVoluntario(VoluntarioInputDTO voluntarioInput);

    public void atualizarVoluntario(VoluntarioInputDTO voluntarioInput);

    public void excluirVoluntario(Integer id);
}
