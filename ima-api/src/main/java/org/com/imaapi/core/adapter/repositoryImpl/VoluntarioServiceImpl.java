package org.com.imaapi.core.adapter.repositoryImpl;

import org.com.imaapi.model.usuario.input.VoluntarioInput;

public interface VoluntarioServiceImpl {
    public void cadastrarVoluntario(VoluntarioInput voluntarioInput);

    public void atualizarVoluntario(VoluntarioInput voluntarioInput);

    public void excluirVoluntario(Integer id);
}
