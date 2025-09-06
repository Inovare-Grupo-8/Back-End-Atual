package org.com.imaapi.application.useCaseImpl;

public interface AssistenteSocialServiceImpl {
	AssistenteSocialOutput cadastrarAssistenteSocial(AssistenteSocialInput input);
	AssistenteSocialOutput atualizarAssistenteSocial(Integer idUsuario, AssistenteSocialInput input);
	AssistenteSocialOutput buscarAssistenteSocial(Integer idUsuario);
}
