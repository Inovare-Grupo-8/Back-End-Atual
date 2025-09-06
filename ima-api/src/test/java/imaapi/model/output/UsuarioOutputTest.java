package imaapi.model.output;

import org.com.imaapi.domain.model.Usuario;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class UsuarioOutputTest {

    @Test
    void deveCriarUsuarioComValoresCorretos() {
        Usuario usuario = new Usuario();
        usuario.setEmail("joao@email.com");
        usuario.setSenha("Senha@123");

        assertEquals("joao@email.com", usuario.getEmail());
    }

    @Test
    void deveGerarDataDeCadastroAutomaticamenteAoPersistir() {
        Usuario usuario = new Usuario();
        usuario.prePersist();
        assertNotNull(usuario.getDataCadastro());
        assertTrue(usuario.getDataCadastro().isBefore(LocalDateTime.now().plusSeconds(1).toLocalDate()));
    }
}

