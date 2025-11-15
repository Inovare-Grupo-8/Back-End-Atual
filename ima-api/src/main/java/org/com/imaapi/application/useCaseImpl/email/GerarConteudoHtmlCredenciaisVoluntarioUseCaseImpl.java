package org.com.imaapi.application.useCaseImpl.email;

import org.com.imaapi.application.useCase.email.GerarConteudoHtmlCredenciaisVoluntarioUseCase;
import org.springframework.stereotype.Service;

@Service
public class GerarConteudoHtmlCredenciaisVoluntarioUseCaseImpl implements GerarConteudoHtmlCredenciaisVoluntarioUseCase {
    @Override
    public String gerar(String nome, String email, String senha) {
        return """
                <!DOCTYPE html>
                <html lang=\"pt-BR\">
                <head>
                    <meta charset=\"UTF-8\">
                    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">
                    <title>Suas credenciais de acesso - IMA Voluntário</title>
                </head>
                <body style=\"font-family: Arial, sans-serif; background-color: #f4f4f9; margin: 0; padding: 0;\">
                    <div style=\"max-width: 600px; margin: 20px auto; background: #ffffff; padding: 20px; border-radius: 8px; box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);\">
                        <div style=\"display: flex; flex-direction: row; align-items: center; justify-content: space-between; padding: 15px; border-bottom: 2px solid #ED4231;\">
                            <img src=\"https://i.ibb.co/MDHNc40s/logo-v2.png\" alt=\"Logo IMA\" style=\"width: 10%%; height: auto;\">
                        </div>
                        <div style=\"margin: 20px 0; text-align: center;\">
                            <h1 style=\"color: #ED4231; font-size: 24px;\">Suas credenciais de acesso</h1>
                            <p style=\"color: #262626; font-size: 16px; line-height: 1.5;\">Olá, <strong>%s</strong>,</p>
                            <p style=\"color: #262626; font-size: 16px; line-height: 1.5;\">Aqui estão suas credenciais para acessar a plataforma do IMA:</p>
                            <div style=\"background-color: #f8f9fa; border: 1px solid #dee2e6; border-radius: 8px; padding: 20px; margin: 20px 0; text-align: left;\">
                                <p style=\"color: #262626; font-size: 16px; margin: 8px 0;\"><strong>Email:</strong> %s</p>
                                <p style=\"color: #262626; font-size: 16px; margin: 8px 0;\"><strong>Senha:</strong> %s</p>
                            </div>
                            <p style=\"color: #dc3545; font-size: 14px; line-height: 1.5; margin: 20px 0;\">
                                <strong>⚠️ Importante:</strong> Por motivos de segurança, recomendamos que você altere sua senha após o primeiro acesso.
                            </p>
                            <a href=\"http://localhost:3030/login\"
                               style=\"display: inline-block; margin-top: 20px; padding: 10px 20px; background-color: #ED4231; color: #f4f4f9; text-decoration: none; font-weight: bold; border-radius: 20px; transition: background-color 0.3s ease;\">Acessar Plataforma</a>
                        </div>
                        <div style=\"text-align: center; font-size: 12px; color: #888; margin-top: 20px; padding-top: 10px; border-top: 1px solid #ddd;\">
                            <p>&copy; 2025 IMA. Todos os direitos reservados.</p>
                        </div>
                    </div>
                </body>
                </html>
                """.formatted(nome, email, senha);
    }
}
