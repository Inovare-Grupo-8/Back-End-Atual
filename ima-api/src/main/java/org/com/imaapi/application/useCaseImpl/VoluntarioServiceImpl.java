package org.com.imaapi.application.useCaseImpl;

public interface VoluntarioServiceImpl {
    public void cadastrarVoluntario(VoluntarioInput voluntarioInput);

    public void atualizarVoluntario(VoluntarioInput voluntarioInput);

    public void excluirVoluntario(Integer id);
}
