package org.com.imaapi.application.useCase.usuario;

import org.com.imaapi.application.dto.usuario.input.AssistenteSocialInput;
import org.com.imaapi.application.dto.usuario.output.AssistenteSocialOutput;

public interface CadastrarAssistenteSocialUseCase {
    AssistenteSocialOutput executar(AssistenteSocialInput input);
}