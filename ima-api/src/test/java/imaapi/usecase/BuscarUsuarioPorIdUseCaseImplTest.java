package imaapi.usecase;

import org.com.imaapi.application.dto.usuario.output.UsuarioOutput;
import org.com.imaapi.application.useCaseImpl.usuario.BuscarUsuarioPorIdUseCaseImpl;
import org.com.imaapi.application.useCaseImpl.endereco.EnderecoUtil;
import org.com.imaapi.domain.model.*;
import org.com.imaapi.domain.model.enums.Funcao;
import org.com.imaapi.domain.model.enums.Genero;
import org.com.imaapi.domain.model.enums.TipoUsuario;
import org.com.imaapi.domain.repository.TelefoneRepository;
import org.com.imaapi.domain.repository.UsuarioRepository;
import org.com.imaapi.domain.repository.VoluntarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BuscarUsuarioPorIdUseCaseImplTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private TelefoneRepository telefoneRepository;

    @Mock
    private VoluntarioRepository voluntarioRepository;

    @Mock
    private EnderecoUtil enderecoUtil;

    @InjectMocks
    private BuscarUsuarioPorIdUseCaseImpl buscarUsuarioPorIdUseCase;

    private Usuario usuario;
    private Ficha ficha;
    private Voluntario voluntario;
    private Endereco endereco;
    private List<Telefone> telefones;

    @BeforeEach
    void setUp() {
        // Criar endereço
        endereco = new Endereco();
        endereco.setIdEndereco(1);
        endereco.setCep("98765432");
        endereco.setLogradouro("Av. Principal");
        endereco.setBairro("Jardim América");
        endereco.setNumero("456");
        endereco.setCidade("Rio de Janeiro");
        endereco.setUf("RJ");

        // Criar ficha
        ficha = new Ficha();
        ficha.setIdFicha(2);
        ficha.setNome("João");
        ficha.setSobrenome("Santos");
        ficha.setCpf("98765432109");
        ficha.setGenero(Genero.MASCULINO);
        ficha.setDtNascim(LocalDate.of(1980, 7, 22));
        ficha.setAreaOrientacao("Direito de Família");
        ficha.setProfissao("Advogado");
        ficha.setEndereco(endereco);

        // Criar usuário
        usuario = new Usuario();
        usuario.setIdUsuario(2);
        usuario.setEmail("joao.santos@email.com");
        usuario.setTipo(TipoUsuario.VOLUNTARIO);
        usuario.setFicha(ficha);
        usuario.setCriadoEm(LocalDateTime.of(2024, 2, 20, 10, 0));

        // Criar voluntário
        voluntario = new Voluntario();
        voluntario.setIdVoluntario(2);
        voluntario.setUsuario(usuario);
        // Simular o valor do banco de dados que será convertido
        // Usar reflection para definir o campo funcao diretamente (simula valor do banco)
        try {
            java.lang.reflect.Field funcaoField = Voluntario.class.getDeclaredField("funcao");
            funcaoField.setAccessible(true);
            funcaoField.set(voluntario, "Advogado");
        } catch (Exception e) {
            // Fallback: usar o método setFuncao com enum
            voluntario.setFuncao(Funcao.JURIDICA);
        }

        // Criar telefones
        telefones = new ArrayList<>();
        Telefone telefone = new Telefone();
        telefone.setIdTelefone(2);
        telefone.setDdd("21");
        telefone.setPrefixo("88888");
        telefone.setSufixo("5678");
        telefone.setWhatsapp(true);
        telefones.add(telefone);
    }

    @Test
    void testExecutar_UsuarioVoluntarioComTodosOsCampos() {
        // Arrange
        when(usuarioRepository.findById(2)).thenReturn(Optional.of(usuario));
        when(voluntarioRepository.findByUsuario_IdUsuario(2)).thenReturn(voluntario);
        when(telefoneRepository.findByFichaIdFicha(2)).thenReturn(telefones);
        when(enderecoUtil.converterParaEnderecoOutput(any())).thenReturn(null); // Simplificado para o teste

        // Act
        Optional<UsuarioOutput> result = buscarUsuarioPorIdUseCase.executar(2);

        // Assert
        assertTrue(result.isPresent());
        UsuarioOutput output = result.get();

        // Verificar dados básicos
        assertEquals(2, output.getId());
        assertEquals("João", output.getNome());
        assertEquals("Santos", output.getSobrenome());
        assertEquals("98765432109", output.getCpf());
        assertEquals("joao.santos@email.com", output.getEmail());
        assertEquals(TipoUsuario.VOLUNTARIO, output.getTipo());

        // Verificar dados da segunda fase
        assertEquals(LocalDate.of(1980, 7, 22), output.getDataNascimento());
        assertEquals("MASCULINO", output.getGenero());
        assertEquals("Direito de Família", output.getAreaOrientacao());
        assertEquals("Advogado", output.getProfissao());

        // Verificar funcao - este é o campo principal que estamos testando
        assertEquals(Funcao.JURIDICA, output.getFuncao());

        // Verificar telefone
        assertNotNull(output.getTelefone());
        assertEquals("21", output.getTelefone().getDdd());
        assertEquals("88888", output.getTelefone().getPrefixo());
        assertEquals("5678", output.getTelefone().getSufixo());
        assertTrue(output.getTelefone().getWhatsapp());

        // Verificar que os métodos foram chamados
        verify(usuarioRepository).findById(2);
        verify(voluntarioRepository).findByUsuario_IdUsuario(2);
        verify(telefoneRepository).findByFichaIdFicha(2);
    }

    @Test
    void testExecutar_UsuarioNaoVoluntario() {
        // Arrange
        usuario.setTipo(TipoUsuario.VALOR_SOCIAL);
        when(usuarioRepository.findById(2)).thenReturn(Optional.of(usuario));
        when(telefoneRepository.findByFichaIdFicha(2)).thenReturn(telefones);

        // Act
        Optional<UsuarioOutput> result = buscarUsuarioPorIdUseCase.executar(2);

        // Assert
        assertTrue(result.isPresent());
        UsuarioOutput output = result.get();

        assertEquals(TipoUsuario.VALOR_SOCIAL, output.getTipo());
        assertNull(output.getFuncao()); // Não deve ter funcao se não for voluntário

        // Verificar que o repositório de voluntário não foi chamado
        verify(voluntarioRepository, never()).findByUsuario_IdUsuario(any());
    }

    @Test
    void testExecutar_UsuarioNaoEncontrado() {
        // Arrange
        when(usuarioRepository.findById(999)).thenReturn(Optional.empty());

        // Act
        Optional<UsuarioOutput> result = buscarUsuarioPorIdUseCase.executar(999);

        // Assert
        assertFalse(result.isPresent());
    }

    @Test
    void testExecutar_IdNulo() {
        // Act
        Optional<UsuarioOutput> result = buscarUsuarioPorIdUseCase.executar(null);

        // Assert
        assertFalse(result.isPresent());
        verify(usuarioRepository, never()).findById(any());
    }

    @Test
    void testFuncaoMapping_PsicologaParaPsicologia() {
        // Arrange - Simular uma psicóloga
        ficha.setProfissao("Psicóloga");
        
        // Usar reflection para definir o campo funcao diretamente (simula valor do banco)
        try {
            java.lang.reflect.Field funcaoField = Voluntario.class.getDeclaredField("funcao");
            funcaoField.setAccessible(true);
            funcaoField.set(voluntario, "Psicóloga");
        } catch (Exception e) {
            // Fallback: usar o método setFuncao com enum
            voluntario.setFuncao(Funcao.PSICOLOGIA);
        }
        
        when(usuarioRepository.findById(2)).thenReturn(Optional.of(usuario));
        when(voluntarioRepository.findByUsuario_IdUsuario(2)).thenReturn(voluntario);
        when(telefoneRepository.findByFichaIdFicha(2)).thenReturn(telefones);

        // Act
        Optional<UsuarioOutput> result = buscarUsuarioPorIdUseCase.executar(2);

        // Assert
        assertTrue(result.isPresent());
        UsuarioOutput output = result.get();
        
        // Verificar que "Psicóloga" foi mapeado para PSICOLOGIA
        assertEquals(Funcao.PSICOLOGIA, output.getFuncao());
    }

    @Test
    void testImprimirResultadoCompleto() {
        // Arrange
        when(usuarioRepository.findById(2)).thenReturn(Optional.of(usuario));
        when(voluntarioRepository.findByUsuario_IdUsuario(2)).thenReturn(voluntario);
        when(telefoneRepository.findByFichaIdFicha(2)).thenReturn(telefones);

        // Act
        Optional<UsuarioOutput> result = buscarUsuarioPorIdUseCase.executar(2);

        // Assert e Imprimir resultado
        assertTrue(result.isPresent());
        UsuarioOutput output = result.get();

        System.out.println("=== RESULTADO DO TESTE ===");
        System.out.println("ID: " + output.getId());
        System.out.println("Nome: " + output.getNome() + " " + output.getSobrenome());
        System.out.println("Email: " + output.getEmail());
        System.out.println("CPF: " + output.getCpf());
        System.out.println("Tipo: " + output.getTipo());
        System.out.println("Funcao: " + output.getFuncao());
        System.out.println("Gênero: " + output.getGenero());
        System.out.println("Data Nascimento: " + output.getDataNascimento());
        System.out.println("Área Orientação: " + output.getAreaOrientacao());
        System.out.println("Profissão: " + output.getProfissao());
        
        if (output.getTelefone() != null) {
            System.out.println("Telefone: (" + output.getTelefone().getDdd() + ") " + 
                             output.getTelefone().getPrefixo() + "-" + output.getTelefone().getSufixo());
            System.out.println("WhatsApp: " + output.getTelefone().getWhatsapp());
        }
        
        System.out.println("Data Cadastro: " + output.getDataCadastro());
        System.out.println("========================");
    }
}