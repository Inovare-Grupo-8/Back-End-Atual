package org.com.imaapi.application.useCase.email;

import org.com.imaapi.application.dto.email.EmailDto;

public interface EnviarEmailUseCase {
    String enviarEmail(EmailDto emailDto);
}