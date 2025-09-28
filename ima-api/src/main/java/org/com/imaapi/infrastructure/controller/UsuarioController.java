package org.com.imaapi.infrastructure.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.com.imaapi.application.dto.usuario.input.UsuarioInputPrimeiraFase;
import org.com.imaapi.application.dto.usuario.input.UsuarioInputSegundaFase;
import org.com.imaapi.application.dto.usuario.output.UsuarioOutput;
import org.com.imaapi.application.dto.usuario.output.UsuarioPrimeiraFaseOutput;
import org.com.imaapi.application.dto.usuario.output.UsuarioListarOutput;
import org.com.imaapi.application.useCase.usuario.*;
import org.com.imaapi.application.dto.usuario.input.UsuarioAutenticacaoInput;
import org.com.imaapi.application.dto.usuario.output.UsuarioTokenOutput;
import org.com.imaapi.application.dto.usuario.output.UsuarioClassificacaoOutput;
import org.com.imaapi.application.dto.usuario.output.VoluntarioListagemOutput;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private CadastrarUsuarioPrimeiraFaseUseCase cadastrarUsuarioPrimeiraFaseUseCase;
    @Autowired private CadastrarUsuarioSegundaFaseUseCase cadastrarUsuarioSegundaFaseUseCase;
    @Autowired private BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase;
    @Autowired private BuscarTodosUsuariosUseCase buscarTodosUsuariosUseCase;
    @Autowired private AtualizarUsuarioUseCase atualizarUsuarioUseCase;
    @Autowired private DeletarUsuarioUseCase deletarUsuarioUseCase;
    @Autowired private AtualizarUltimoAcessoUseCase atualizarUltimoAcessoUseCase;
    @Autowired private AutenticarUsuarioUseCase autenticarUsuarioUseCase;
    @Autowired private BuscarDadosPrimeiraFaseUseCase buscarDadosPrimeiraFaseUseCase;
    @Autowired private BuscarUsuarioPorEmailUseCase buscarUsuarioPorEmailUseCase;
    @Autowired private BuscarUsuarioPorNomeUseCase buscarUsuarioPorNomeUseCase;
    @Autowired private BuscarUsuariosNaoClassificadosUseCase buscarUsuariosNaoClassificadosUseCase;
    @Autowired private CadastrarUsuarioOAuthUseCase cadastrarUsuarioOAuthUseCase;
    @Autowired private CadastrarVoluntarioPrimeiraFaseUseCase cadastrarVoluntarioPrimeiraFaseUseCase;
    @Autowired private CadastrarVoluntarioSegundaFaseUseCase cadastrarVoluntarioSegundaFaseUseCase;
    @Autowired private ClassificarUsuarioComoGratuidadeUseCase classificarUsuarioComoGratuidadeUseCase;
    @Autowired private ClassificarUsuarioComoValorSocialUseCase classificarUsuarioComoValorSocialUseCase;
    @Autowired private EnviarCredenciaisVoluntarioUseCase enviarCredenciaisVoluntarioUseCase;
    @Autowired private ListarVoluntariosUseCase listarVoluntariosUseCase;

    private static final Logger LOGGER = LoggerFactory.getLogger(UsuarioController.class);
    
    @PostMapping("/primeira-fase")
    public ResponseEntity<UsuarioPrimeiraFaseOutput> cadastrarPrimeiraFase(@Valid @RequestBody UsuarioInputPrimeiraFase input) {
        LOGGER.info("Iniciando cadastro de usuário (primeira fase) para email: {}", input.getEmail());
        UsuarioPrimeiraFaseOutput output = cadastrarUsuarioPrimeiraFaseUseCase.executar(input);
        LOGGER.info("Usuário cadastrado com sucesso (primeira fase) para email: {}", input.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).body(output);
    }

    @PostMapping("/voluntario/primeira-fase")
    public ResponseEntity<UsuarioPrimeiraFaseOutput> cadastrarVoluntarioPrimeiraFase(@Valid @RequestBody UsuarioInputPrimeiraFase input) {
        LOGGER.info("Iniciando cadastro de voluntário (primeira fase) para email: {}", input.getEmail());
        UsuarioPrimeiraFaseOutput output = cadastrarVoluntarioPrimeiraFaseUseCase.executar(input);
        LOGGER.info("Voluntário cadastrado com sucesso (primeira fase) para email: {}", input.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).body(output);
    }

    @PostMapping("/segunda-fase")
    public ResponseEntity<UsuarioOutput> cadastrarSegundaFase(@RequestParam Integer idUsuario, @Valid @RequestBody UsuarioInputSegundaFase input) {
        LOGGER.info("Iniciando cadastro de usuário (segunda fase) para idUsuario: {}", idUsuario);
        UsuarioOutput output = cadastrarUsuarioSegundaFaseUseCase.executar(idUsuario, input);
        LOGGER.info("Usuário cadastrado com sucesso (segunda fase) para idUsuario: {}", idUsuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(output);
    }

    @PostMapping("/voluntario/segunda-fase")
    public ResponseEntity<UsuarioOutput> cadastrarVoluntarioSegundaFase(@RequestParam Integer idUsuario, @Valid @RequestBody UsuarioInputSegundaFase input) {
        LOGGER.info("Iniciando cadastro de voluntário (segunda fase) para idUsuario: {}", idUsuario);
        UsuarioOutput output = cadastrarVoluntarioSegundaFaseUseCase.executar(idUsuario, input);
        LOGGER.info("Voluntário cadastrado com sucesso (segunda fase) para idUsuario: {}", idUsuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(output);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioOutput> buscarPorId(@PathVariable Integer id) {
        LOGGER.info("Buscando usuário por id: {}", id);
        return buscarUsuarioPorIdUseCase.executar(id)
                .map(usuario -> {
                    LOGGER.info("Usuário encontrado para id: {}", id);
                    return ResponseEntity.ok(usuario);
                })
                .orElseGet(() -> {
                    LOGGER.warn("Usuário não encontrado para id: {}", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @GetMapping
    public ResponseEntity<List<UsuarioListarOutput>> listarTodos() {
        LOGGER.info("Listando todos os usuários");
        java.util.List<UsuarioListarOutput> lista = buscarTodosUsuariosUseCase.executar();
        LOGGER.info("Total de usuários encontrados: {}", lista.size());
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UsuarioOutput> buscarPorEmail(@PathVariable String email) {
        LOGGER.info("Buscando usuário por email: {}", email);
        return buscarUsuarioPorEmailUseCase.executar(email)
                .map(usuario -> {
                    LOGGER.info("Usuário encontrado para email: {}", email);
                    return ResponseEntity.ok(usuario);
                })
                .orElseGet(() -> {
                    LOGGER.warn("Usuário não encontrado para email: {}", email);
                    return ResponseEntity.notFound().build();
                });
    }

    @GetMapping("/nome/{termo}")
    public ResponseEntity<UsuarioOutput> buscarPorNome(@PathVariable String termo) {
        LOGGER.info("Buscando usuário por nome: {}", termo);
        return buscarUsuarioPorNomeUseCase.executar(termo)
                .map(usuario -> {
                    LOGGER.info("Usuário encontrado para nome: {}", termo);
                    return ResponseEntity.ok(usuario);
                })
                .orElseGet(() -> {
                    LOGGER.warn("Usuário não encontrado para nome: {}", termo);
                    return ResponseEntity.notFound().build();
                });
    }

    @GetMapping("/nao-classificados")
    public ResponseEntity<List<UsuarioClassificacaoOutput>> listarNaoClassificados() {
        LOGGER.info("Listando usuários não classificados");
        java.util.List<UsuarioClassificacaoOutput> lista = buscarUsuariosNaoClassificadosUseCase.executar();
        LOGGER.info("Total de usuários não classificados: {}", lista.size());
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/voluntarios")
    public ResponseEntity<java.util.List<VoluntarioListagemOutput>> listarVoluntarios() {
        LOGGER.info("Listando voluntários");
        java.util.List<VoluntarioListagemOutput> lista = listarVoluntariosUseCase.executar();
        LOGGER.info("Total de voluntários encontrados: {}", lista.size());
        return ResponseEntity.ok(lista);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioListarOutput> atualizar(@PathVariable Integer id, @Valid @RequestBody UsuarioInputSegundaFase input) {
        LOGGER.info("Atualizando usuário id: {}", id);
        UsuarioListarOutput output = atualizarUsuarioUseCase.executar(id, input);
        LOGGER.info("Usuário atualizado com sucesso id: {}", id);
        return ResponseEntity.ok(output);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        LOGGER.info("Deletando usuário id: {}", id);
        deletarUsuarioUseCase.executar(id);
        LOGGER.info("Usuário deletado com sucesso id: {}", id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/ultimo-acesso")
    public ResponseEntity<Void> atualizarUltimoAcesso(@PathVariable Integer id) {
        LOGGER.info("Atualizando último acesso do usuário id: {}", id);
        atualizarUltimoAcessoUseCase.executar(id);
        LOGGER.info("Último acesso atualizado para usuário id: {}", id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/autenticar")
    public ResponseEntity<UsuarioTokenOutput> autenticar(@Valid @RequestBody UsuarioAutenticacaoInput input) {
        LOGGER.info("Autenticando usuário: {}", input.getEmail());
        UsuarioTokenOutput token = autenticarUsuarioUseCase.executar(input);
        LOGGER.info("Usuário autenticado com sucesso: {}", input.getEmail());
        return ResponseEntity.ok(token);
    }

    @GetMapping("/primeira-fase/{id}")
    public ResponseEntity<UsuarioPrimeiraFaseOutput> buscarDadosPrimeiraFasePorId(@PathVariable Integer id) {
        LOGGER.info("Buscando dados da primeira fase para usuário id: {}", id);
        return ResponseEntity.ok(buscarDadosPrimeiraFaseUseCase.executarPorId(id));
    }

    @GetMapping("/primeira-fase/email/{email}")
    public ResponseEntity<UsuarioPrimeiraFaseOutput> buscarDadosPrimeiraFasePorEmail(@PathVariable String email) {
        LOGGER.info("Buscando dados da primeira fase para email: {}", email);
        return ResponseEntity.ok(buscarDadosPrimeiraFaseUseCase.executarPorEmail(email));
    }

    @PostMapping("/oauth")
    public ResponseEntity<Void> cadastrarOAuth(@RequestBody Object usuarioOAuth) {
        LOGGER.info("Chamada para cadastro OAuth recebida, funcionalidade não implementada.");
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @PostMapping("/classificar/gratuidade/{id}")
    public ResponseEntity<UsuarioListarOutput> classificarComoGratuidade(@PathVariable Integer id) {
        LOGGER.info("Classificando usuário como gratuidade id: {}", id);
        UsuarioListarOutput output = classificarUsuarioComoGratuidadeUseCase.executar(id);
        LOGGER.info("Usuário classificado como gratuidade id: {}", id);
        return ResponseEntity.ok(output);
    }

    @PostMapping("/classificar/valor-social/{id}")
    public ResponseEntity<UsuarioListarOutput> classificarComoValorSocial(@PathVariable Integer id) {
        LOGGER.info("Classificando usuário como valor social id: {}", id);
        UsuarioListarOutput output = classificarUsuarioComoValorSocialUseCase.executar(id);
        LOGGER.info("Usuário classificado como valor social id: {}", id);
        return ResponseEntity.ok(output);
    }

    @PostMapping("/voluntario/credenciais")
    public ResponseEntity<String> enviarCredenciaisVoluntario(@RequestParam String email, @RequestParam String nome, @RequestParam String senha) {
        LOGGER.info("Enviando credenciais para voluntário: {} - {}", nome, email);
        String resultado = enviarCredenciaisVoluntarioUseCase.executar(email, nome, senha);
        LOGGER.info("Credenciais enviadas para voluntário: {} - {}", nome, email);
        return ResponseEntity.ok(resultado);
    }
}