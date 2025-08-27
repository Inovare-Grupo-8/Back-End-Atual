package org.com.imaapi.core.adapter.repositoryImpl;

import org.com.imaapi.model.usuario.input.AssistenteSocialInput;
import org.com.imaapi.model.usuario.output.AssistenteSocialOutput;

public interface AssistenteSocialServiceImpl {
	AssistenteSocialOutput cadastrarAssistenteSocial(AssistenteSocialInput input);
	AssistenteSocialOutput atualizarAssistenteSocial(Integer idUsuario, AssistenteSocialInput input);
	AssistenteSocialOutput buscarAssistenteSocial(Integer idUsuario);
}
