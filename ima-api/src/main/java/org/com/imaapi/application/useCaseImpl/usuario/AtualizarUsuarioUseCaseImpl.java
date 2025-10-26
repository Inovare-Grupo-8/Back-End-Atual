package org.com.imaapi.application.useCaseImpl.usuario;


import org.com.imaapi.application.dto.usuario.input.UsuarioInputSegundaFase;
import org.com.imaapi.application.dto.usuario.output.UsuarioListarOutput;
import org.com.imaapi.application.useCase.usuario.AtualizarUsuarioUseCase;
import org.com.imaapi.application.useCase.usuario.CadastrarUsuarioSegundaFaseUseCase;
import org.com.imaapi.application.dto.usuario.output.UsuarioOutput;
import org.com.imaapi.domain.model.Usuario;
import org.com.imaapi.domain.repository.UsuarioRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AtualizarUsuarioUseCaseImpl implements AtualizarUsuarioUseCase {

    private final UsuarioRepository usuarioRepository;
    private final CadastrarUsuarioSegundaFaseUseCase cadastrarUsuarioSegundaFaseUseCase;

    public AtualizarUsuarioUseCaseImpl(UsuarioRepository usuarioRepository,
                                      CadastrarUsuarioSegundaFaseUseCase cadastrarUsuarioSegundaFaseUseCase) {
        this.usuarioRepository = usuarioRepository;
        this.cadastrarUsuarioSegundaFaseUseCase = cadastrarUsuarioSegundaFaseUseCase;
    }

    @Override
    @Transactional
    public UsuarioListarOutput executar(Integer id, UsuarioInputSegundaFase usuarioInputSegundaFase) {
        if (usuarioInputSegundaFase == null) {
            throw new IllegalArgumentException("Dados de atualização não podem ser nulos");
        }

        // Reaproveitar a lógica de segunda fase (criar/atualizar) que já trata ficha, endereco, telefone e voluntario
        UsuarioOutput usuarioOutput = cadastrarUsuarioSegundaFaseUseCase.executar(id, usuarioInputSegundaFase);

        if (usuarioOutput == null) {
            throw new IllegalArgumentException("Erro ao atualizar usuário: saída nula");
        }

        UsuarioListarOutput output = new UsuarioListarOutput();
        // Copiar propriedades relevantes do UsuarioOutput para UsuarioListarOutput
        BeanUtils.copyProperties(usuarioOutput, output);

        return output;
    }
}
