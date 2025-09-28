package org.com.imaapi.infrastructure.controller;

@RestController
@RequestMapping("/perfil")
@CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.PATCH, RequestMethod.OPTIONS})
public class PerfilController {

    @Autowired
    private PerfilServiceImpl perfilService;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private AssistenteSocialServiceImpl assistenteSocialService;

    private static final Logger LOGGER = LoggerFactory.getLogger(PerfilController.class);

    @GetMapping("/{tipo}/dados-pessoais")
    public ResponseEntity<UsuarioDadosPessoaisOutput> buscarDadosPessoais(
            @RequestParam Integer usuarioId, @PathVariable String tipo) {
        UsuarioDadosPessoaisOutput usuarioOutput = perfilService.buscarDadosPessoaisPorId(usuarioId);
        if (usuarioOutput == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(usuarioOutput);
    }

    @PatchMapping("/{tipo}/dados-pessoais")
    public ResponseEntity<UsuarioOutput> atualizarDadosPessoais(
            @RequestParam Integer usuarioId,
            @PathVariable String tipo,
            @RequestBody UsuarioInputAtualizacaoDadosPessoais usuarioInputAtualizacaoDadosPessoais) {
        UsuarioOutput usuarioOutput = perfilService.atualizarDadosPessoais(usuarioId, usuarioInputAtualizacaoDadosPessoais);
        if (usuarioOutput == null) {
            return ResponseEntity.status(404).body(null);
        }
        return ResponseEntity.ok(usuarioOutput);
    }

    @GetMapping("/{tipo}/endereco")
    public ResponseEntity<EnderecoOutput> buscarEndereco(
            @RequestParam Integer usuarioId, @PathVariable String tipo) {
        EnderecoOutput enderecoOutput = perfilService.buscarEnderecoPorId(usuarioId);
        if (enderecoOutput == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(enderecoOutput);
    }

    @PutMapping("/{tipo}/endereco")
    public ResponseEntity<Void> atualizarEndereco(
            @RequestParam Integer usuarioId,
            @PathVariable String tipo,
            @RequestBody @Valid EnderecoInput enderecoInput) {
        boolean atualizado = perfilService.atualizarEnderecoPorUsuarioId(
                usuarioId, enderecoInput.getCep(), enderecoInput.getNumero(), enderecoInput.getComplemento());
        if (!atualizado) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{tipo}/foto")
    @Operation(summary = "Upload de foto do perfil", 
               description = "Realiza o upload de uma foto para o perfil do usuário com validações de tipo e tamanho")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Foto enviada com sucesso",
                    content = @Content(schema = @Schema(implementation = FotoUploadOutput.class))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos ou arquivo com problema",
                    content = @Content(schema = @Schema(implementation = FotoUploadOutput.class))),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor",
                    content = @Content(schema = @Schema(implementation = FotoUploadOutput.class)))
    })
    public ResponseEntity<FotoUploadOutput> uploadFoto(
            @Parameter(description = "ID do usuário") @RequestParam Integer usuarioId,
            @Parameter(description = "Tipo do usuário (assistido, voluntario, assistente_social)") @PathVariable String tipo,
            @Parameter(description = "Arquivo de imagem (máximo 1MB, formatos: JPEG, PNG, GIF, WEBP)") @RequestParam("file") MultipartFile file) {
        
        LOGGER.info("Iniciando upload de foto para usuário ID: {}, tipo: {}", usuarioId, tipo);
        
        // Criar DTO de input
        FotoUploadInput input = new FotoUploadInput(usuarioId, tipo, file);
        
        // Validações usando método utilitário
        FotoUploadOutput validationError = validarFotoInput(input, usuarioId);
        if (validationError != null) {
            return ResponseEntity.badRequest().body(validationError);
        }

        try {
            String fotoUrl = perfilService.salvarFoto(usuarioId, tipo, file);
            
            LOGGER.info("Foto salva com sucesso para usuário ID: {}, URL: {}", usuarioId, fotoUrl);
            
            // Criar resposta usando DTO de output
            FotoUploadOutput output = FotoUploadOutput.sucesso(
                    "Foto salva com sucesso.",
                    fotoUrl,
                    file.getOriginalFilename(),
                    Long.valueOf(file.getSize()),
                    file.getContentType()
            );
            
            return ResponseEntity.ok(output);
            
        } catch (IOException e) {
            LOGGER.error("Erro ao salvar foto para usuário ID: {}: {}", usuarioId, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(FotoUploadOutput.erro("Erro interno ao salvar a foto."));
        } catch (Exception e) {
            LOGGER.error("Erro inesperado ao processar upload para usuário ID: {}: {}", usuarioId, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(FotoUploadOutput.erro("Erro inesperado ao processar o upload."));
        }
    }

    /**
     * Valida os dados de entrada para upload de foto
     * @param input DTO com os dados do upload
     * @return FotoUploadOutput com erro se houver problemas, null se válido
     */
    private FotoUploadOutput validarFotoInput(FotoUploadInput input, Integer usuarioId) {
        if (input.isArquivoVazio()) {
            LOGGER.warn("Arquivo vazio recebido para usuário ID: {}", usuarioId);
            return FotoUploadOutput.erro("O arquivo não pode estar vazio.");
        }

        if (!input.isTipoValido()) {
            LOGGER.warn("Tipo de arquivo inválido recebido: {} para usuário ID: {}", 
                    input.getArquivo().getContentType(), usuarioId);
            return FotoUploadOutput.erro("O arquivo deve ser uma imagem válida (JPEG, PNG, GIF, WEBP).");
        }

        if (!input.isTamanhoValido()) {
            LOGGER.warn("Arquivo muito grande ({} bytes) recebido para usuário ID: {}", 
                    input.getArquivo().getSize(), usuarioId);
            return FotoUploadOutput.erro("O tamanho máximo permitido é 1MB.");
        }

        return null; // Validação passou
    }

    @PatchMapping("/voluntario/dados-profissionais")
    public ResponseEntity<Void> atualizarDadosProfissionais(
            @RequestParam Integer usuarioId,
            @RequestBody @Valid VoluntarioDadosProfissionaisInput dadosProfissionais) {
        boolean atualizado = perfilService.atualizarDadosProfissionais(usuarioId, dadosProfissionais);
        if (!atualizado) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/voluntario/disponibilidade")
    public ResponseEntity<Void> criarDisponibilidade(
            @RequestParam Integer usuarioId,
            @RequestBody Map<String, Object> disponibilidade) {
        boolean criado = perfilService.criarDisponibilidade(usuarioId, disponibilidade);
        if (!criado) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(201).build();
    }

    @PatchMapping("/voluntario/disponibilidade")
    public ResponseEntity<Void> atualizarDisponibilidade(
            @RequestParam Integer usuarioId,
            @RequestBody Map<String, Object> disponibilidade) {
        boolean atualizado = perfilService.atualizarDisponibilidade(usuarioId, disponibilidade);
        if (!atualizado) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/assistente-social")
    public ResponseEntity<AssistenteSocialOutput> buscarPerfilAssistenteSocial(@RequestParam Integer usuarioId) {
        try {
            AssistenteSocialOutput perfil = assistenteSocialService.buscarAssistenteSocial(usuarioId);
            if (perfil == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(perfil);
        } catch (Exception e) {
            LOGGER.error("Erro ao buscar perfil do assistente social: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PatchMapping("/assistente-social/dados-profissionais")
    @ResponseBody
    public ResponseEntity<Void> atualizarDadosProfissionaisAssistenteSocial(
            @RequestParam Integer usuarioId,
            @RequestBody @Valid VoluntarioDadosProfissionaisInput dadosProfissionais) {
        try {
            LOGGER.info("Atualizando dados profissionais para assistente social com ID: {}", usuarioId);
            boolean atualizado = perfilService.atualizarDadosProfissionais(usuarioId, dadosProfissionais);
            if (!atualizado) {
                LOGGER.warn("Assistente social não encontrado com ID: {}", usuarioId);
                return ResponseEntity.notFound().build();
            }
            LOGGER.info("Dados profissionais atualizados com sucesso para assistente social com ID: {}", usuarioId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            LOGGER.error("Erro ao atualizar dados profissionais do assistente social: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }    
  
  @PatchMapping("/assistente-social/dados-pessoais")
    @ResponseBody
    public ResponseEntity<UsuarioDadosPessoaisOutput> atualizarDadosPessoaisAssistenteSocial(
            @RequestParam Integer usuarioId,
            @RequestBody @Valid UsuarioInputAtualizacaoDadosPessoais dadosPessoais) {
        try {
            LOGGER.info("Atualizando dados pessoais para assistente social com ID: {}", usuarioId);
            UsuarioDadosPessoaisOutput dadosAtualizados = perfilService.atualizarDadosPessoaisCompleto(usuarioId, dadosPessoais);
            if (dadosAtualizados == null) {
                LOGGER.warn("Assistente social não encontrado com ID: {}", usuarioId);
                return ResponseEntity.notFound().build();
            }
            LOGGER.info("Dados pessoais atualizados com sucesso para assistente social com ID: {}", usuarioId);
            return ResponseEntity.ok(dadosAtualizados);
        } catch (Exception e) {
            LOGGER.error("Erro ao atualizar dados pessoais do assistente social: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}