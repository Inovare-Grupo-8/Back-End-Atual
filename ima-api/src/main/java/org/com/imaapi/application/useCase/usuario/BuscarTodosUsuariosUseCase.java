package org.com.imaapi.application.useCase.usuario;

import org.com.imaapi.application.dto.usuario.output.UsuarioListarOutput;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BuscarTodosUsuariosUseCase {
    List<UsuarioListarOutput> executar();
    Page<UsuarioListarOutput> executarComPaginacao(Pageable pageable);
}