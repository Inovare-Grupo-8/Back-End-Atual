package org.com.imaapi.infrastructure.controller;

@Controller
@RequestMapping("/oauth2")
public class Oauth2Controller {

    private final OauthTokenServiceImpl oauthTokenService;
    private final UsuarioRepository usuarioRepository;

    public Oauth2Controller(UsuarioRepository usuarioRepository,
                            OauthTokenServiceImpl oauthTokenService) {

        this.usuarioRepository = usuarioRepository;
        this.oauthTokenService = oauthTokenService;
    }

    @GetMapping("/authorize/calendar")
    public ResponseEntity<?> authorizeCalendar(Authentication authentication) throws IOException {
        try {
            OAuth2User usuarioOauth = (OAuth2User) authentication.getPrincipal();
            System.out.println(usuarioOauth);
            String email = usuarioOauth.getAttribute("email");

            Usuario usuario = usuarioRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

            String state = "calendar_" + UUID.randomUUID().toString();

            Set<String> additionalScopes = Set.of(
                    "https://www.googleapis.com/auth/calendar.app.created"
            );

            String authorizationUrl = oauthTokenService.construirUrl(additionalScopes, state);

            System.out.println(authorizationUrl);

            return ResponseEntity.status(HttpStatus.FOUND)
                    .header("Location", authorizationUrl)
                    .build();

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }
}
