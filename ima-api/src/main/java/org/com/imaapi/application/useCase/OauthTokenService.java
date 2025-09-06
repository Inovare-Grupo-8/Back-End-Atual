package org.com.imaapi.application.usecase;

@Service
public class OauthTokenService implements OauthTokenService {

    private final OauthTokenRepository oauthTokenRepository;
    private final UsuarioRepository usuarioRepository;
    private final ClientRegistrationRepository clientRegistrationRepository;
    private final OAuth2AuthorizedClientManager oauthClientManager;

    public OauthTokenService(OauthTokenRepository oauthTokenRepository,
                             OAuth2AuthorizedClientManager oauthClientManager,
                             UsuarioRepository usuarioRepository,
                             ClientRegistrationRepository clientRegistrationRepository) {
        this.oauthTokenRepository = oauthTokenRepository;
        this.oauthClientManager = oauthClientManager;
        this.usuarioRepository = usuarioRepository;
        this.clientRegistrationRepository = clientRegistrationRepository;
    }

    @Override
    public OAuth2AccessToken renovarAccessToken(Authentication authentication) {
        OAuth2AuthorizeRequest authorizeRequest = OAuth2AuthorizeRequest.withClientRegistrationId("google")
                .principal(authentication)
                .build();

        OAuth2AuthorizedClient authorizedClient = oauthClientManager.authorize(authorizeRequest);

        if (authorizedClient == null) {
            throw new IllegalStateException("Usuário não autorizado ou token expirado e refresh token indisponível.");
        }

        Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail(authentication.getName());

        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();

            OAuth2AccessToken accessToken = authorizedClient.getAccessToken();
            OAuth2RefreshToken refreshToken = authorizedClient.getRefreshToken();

            salvarToken(usuario, accessToken, refreshToken);

        } else {
            throw new IllegalStateException("Usuário não cadastrado ou token expirado.");
        }

        return authorizedClient.getAccessToken();
    }

    @Override
    public void salvarToken(Usuario usuario, OAuth2AccessToken accessToken, OAuth2RefreshToken refreshToken) {
        OauthToken oauthToken = oauthTokenRepository.findByUsuarioIdUsuario(usuario.getIdUsuario())
                .orElse(new OauthToken());

        oauthToken.setUsuario(usuario);

        oauthToken.atualizarTokens(
                accessToken.getTokenValue(),
                refreshToken != null ? refreshToken.getTokenValue() : null,
                accessToken.getExpiresAt()
        );

        oauthTokenRepository.save(oauthToken);
    }

    public String construirUrl(Set<String> escoposAdicionais, String state) {

        ClientRegistration googleRegistration = clientRegistrationRepository.findByRegistrationId("google");

        Set<String> escoposCombinados = new HashSet<>(googleRegistration.getScopes());
        escoposCombinados.addAll(escoposAdicionais);

        return UriComponentsBuilder
                .fromUriString(googleRegistration.getProviderDetails().getAuthorizationUri())
                .queryParam("client_id", googleRegistration.getClientId())
                .queryParam("redirect_uri", googleRegistration.getRedirectUri())
                .queryParam("response_type", "code")
                .queryParam("scope", String.join(" ", escoposCombinados))
                .queryParam("state", state)
                .queryParam("access_type", "offline")
                .queryParam("prompt", "consent")
                .build()
                .toUriString();
    }
}
