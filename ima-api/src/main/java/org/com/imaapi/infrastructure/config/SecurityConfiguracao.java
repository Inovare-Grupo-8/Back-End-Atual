package org.com.imaapi.infrastructure.config;

@Configuration
@EnableWebSecurity
public class SecurityConfiguracao {    private static final AntPathRequestMatcher[] URLS_PERMITIDAS = {
            new AntPathRequestMatcher("/swagger-ui/**"),
            new AntPathRequestMatcher("/swagger-ui.html"),
            new AntPathRequestMatcher("/swagger-resources"),
            new AntPathRequestMatcher("/swagger-resources/**"),
            new AntPathRequestMatcher("/configuration/ui"),
            new AntPathRequestMatcher("/configuration/security"),
            new AntPathRequestMatcher("/api/public/**"),
            new AntPathRequestMatcher("/api/public/authenticate"),
            new AntPathRequestMatcher("/webjars/**"),
            new AntPathRequestMatcher("/v3/api-docs/**"),
            new AntPathRequestMatcher("/actuator/**"),
            new AntPathRequestMatcher("/usuarios/**"),
            new AntPathRequestMatcher("/assistentes-sociais/**"),            
            new AntPathRequestMatcher("/usuarios/login/***"),
            new AntPathRequestMatcher("/h2-console/**"),
            new AntPathRequestMatcher("/h2-console/**/**"),
            new AntPathRequestMatcher("/perfil/**"),
            new AntPathRequestMatcher("/error/**"),
            new AntPathRequestMatcher("/"),
            new AntPathRequestMatcher("/oauth2/**"),
            new AntPathRequestMatcher("/oauth2/authorization/google"),
            new AntPathRequestMatcher("/dev/token"),
            new AntPathRequestMatcher("/uploads/**")  // Add this line
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   AutenticacaoSucessHandler autenticacaoSucessHandler,
                                                   AutenticacaoEntryPoint autenticacaoEntryPoint,
                                                   EscopoIncrementalFilter escopoIncrementalFilter) throws Exception {
        return http
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .cors(Customizer.withDefaults())
                .csrf(CsrfConfigurer<HttpSecurity>::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/usuarios/voluntario").hasRole("ADMINISTRADOR")
                        .requestMatchers(URLS_PERMITIDAS)
                        .permitAll()
                        .anyRequest().authenticated()  // Requer autenticação para tudo
                )
                .oauth2Login(oauth2 -> oauth2
                        .successHandler(autenticacaoSucessHandler)
                )
                .exceptionHandling(handling -> handling
                        .authenticationEntryPoint(autenticacaoEntryPoint))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                .addFilterBefore(jwtAuthenticationFilterBean(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(escopoIncrementalFilter, OAuth2AuthorizationRequestRedirectFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.authenticationProvider(new AutenticacaoProvider(autenticacaoService(), passwordEncoder()));
        return authenticationManagerBuilder.build();
    }

    @Bean
    public AutenticacaoEntryPoint jwtAuthenticationEntryPointBean() {
        return new AutenticacaoEntryPoint();
    }

    @Bean
    public GerenciadorTokenJwt jwtAuthenticationUtilBean() {
        return new GerenciadorTokenJwt();
    }

    @Bean
    public AutenticacaoFilter jwtAuthenticationFilterBean() {
        return new AutenticacaoFilter(autenticacaoService(), jwtAuthenticationUtilBean());
    }

    @Bean
    public AutenticacaoSucessHandler autenticacaoSucessHandler(
            UsuarioRepository usuarioRepository,
            FichaRepository fichaRepository,
            GerenciadorTokenJwt gerenciadorTokenJwt,
            UsuarioServiceImpl usuarioService,
            OAuth2AuthorizedClientManager authorizedClientManager,
            OauthTokenServiceImpl oauthTokenService) {

        return new AutenticacaoSucessHandler(
                usuarioRepository,
                fichaRepository,
                gerenciadorTokenJwt,
                usuarioService,
                authorizedClientManager,
                oauthTokenService
        );
    }

    @Bean
    public EscopoIncrementalFilter escopoIncrementalFilter(
            OAuth2AuthorizedClientManager authorizedClientManager,
            OauthTokenServiceImpl oauthTokenService,
            UsuarioRepository usuarioRepository) {
        return new EscopoIncrementalFilter(
                authorizedClientManager,
                oauthTokenService,
                usuarioRepository
        );
    }

    @Bean
    public AuthenticationEntryPoint autenticacaoEntryPoint() {
        return new AutenticacaoEntryPoint();
    }

    @Bean
    public AutenticacaoServiceImpl autenticacaoService() {
        return new AutenticacaoServiceImpl();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Wrap the standard password encoder with our logging version
        return new LoggingPasswordEncoder(new BCryptPasswordEncoder());
    }
                                   
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuracao = new CorsConfiguration();
        configuracao.setAllowedOrigins(Arrays.asList("http://localhost:3030", "http://localhost:5173"));
        configuracao.setAllowedMethods(
                Arrays.asList(
                        HttpMethod.GET.name(),
                        HttpMethod.POST.name(),
                        HttpMethod.PUT.name(),
                        HttpMethod.PATCH.name(),
                        HttpMethod.DELETE.name(),
                        HttpMethod.OPTIONS.name(),
                        HttpMethod.HEAD.name(),
                        HttpMethod.TRACE.name()
                )
        );

        // Allow all headers to fix CORS issues
        configuracao.setAllowedHeaders(List.of("*"));
        configuracao.setAllowCredentials(true);

        configuracao.setExposedHeaders(List.of(
                HttpHeaders.CONTENT_DISPOSITION,
                "Authorization"
        ));

        UrlBasedCorsConfigurationSource origem = new UrlBasedCorsConfigurationSource();
        origem.registerCorsConfiguration("/**", configuracao);

        return origem;
    }
}
