package org.com.imaapi.application.useCase.usuario;

import org.com.imaapi.application.dto.usuario.output.VoluntarioListagemOutput;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ListarVoluntariosUseCase {
    List<VoluntarioListagemOutput> executar();
    Slice<VoluntarioListagemOutput> executarComPaginacao(Pageable pageable);
}