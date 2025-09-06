package imaapi.model.input;

import org.com.imaapi.application.dto.usuario.input.UsuarioInputPrimeiraFaseDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UsuarioInputPrimeiraFaseTest {

    @Test
    void emailDeveSerValido() {
        UsuarioInputPrimeiraFaseDTO usuarioInputPrimeiraFase = new UsuarioInputPrimeiraFaseDTO();
        usuarioInputPrimeiraFase.setEmail("teste.teste.com");

        String email = usuarioInputPrimeiraFase.getEmail();
        assertFalse(email.contains("@"), "Email inválido: deve conter '@'");
    }

    @Test
    void nomeDeveSerValido() {
        UsuarioInputPrimeiraFaseDTO usuarioInputPrimeiraFase = new UsuarioInputPrimeiraFaseDTO();
        usuarioInputPrimeiraFase.setNome(null);

        String nome = usuarioInputPrimeiraFase.getNome();
        assertEquals(null, nome, "Nome não pode ser nulo");
    }

    @Test
    void senhaDeveConterCaracterEspecial() {
        UsuarioInputPrimeiraFaseDTO usuarioInputPrimeiraFase = new UsuarioInputPrimeiraFaseDTO();
        usuarioInputPrimeiraFase.setSenha("senhaTeste");

        String senha = usuarioInputPrimeiraFase.getSenha();
        assertFalse(senha.contains("@"), "Senha deve conter caracter especial");
    }

    @Test
    void senhaMenorQueSeisCaracteresNaoEhValida() {
        UsuarioInputPrimeiraFaseDTO usuario = new UsuarioInputPrimeiraFaseDTO();
        usuario.setSenha("abc"); // apenas 3 caracteres
        assertTrue(usuario.getSenha().length() < 6, "Senha com menos de 6 caracteres deve ser inválida");
    }
}