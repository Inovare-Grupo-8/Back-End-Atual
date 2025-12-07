package org.com.imaapi.infrastructure.config;

import org.com.imaapi.application.useCase.usuario.BuscarUsuarioPorEmailUseCase;
import org.com.imaapi.application.useCase.usuario.CadastrarUsuarioPrimeiraFaseUseCase;
import org.com.imaapi.domain.gateway.PasswordEncoderGateway;
import org.com.imaapi.infrastructure.adapter.AutenticacaoServiceAdapter;
import org.com.imaapi.infrastructure.adapter.GerenciadorTokenJwtAdapter;
import org.com.imaapi.infrastructure.adapter.PasswordEncoderAdapter;
import org.com.imaapi.infrastructure.config.autenticacao.*;
import org.com.imaapi.infrastructure.config.autenticacao.handler.AutenticacaoSucessHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.*;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private static final String[] URLS_PUBLICAS = {
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/error/**",
            "/",
            "/usuarios/primeira-fase/**",
            "/usuarios/segunda-fase/**",
            "/usuarios/login/**"
    };

    private static final String[] URLS_ADMINISTRADORES = {
            "/assistentes-sociais/**",
            "/assistentes-sociais/{id}",
            "/assistentes-sociais/perfil/**",
            "/especialidade/**",
            "/perfil/assistente-social/**",
            "/usuarios/voluntario/primeira-fase",
            "/usuarios/voluntario/segunda-fase",
            "/usuarios/{id}",
            "/usuarios/paginado",
            "/usuarios/email/{email}",
            "/usuarios/nome/{termo}",
            "/usuarios/nao-classificados"
    };

    private static final String[] URLS_VOLUNTARIOS = {
            "/usuarios/voluntario/**",
            "/disponibilidade/**"
    };

    private static final String[] URLS_INTERNOS = {
            "/consulta/consultas/{idConsulta}/avaliacoes",
            "/consulta/consultas/{idConsulta}/feedbacks",
            "/consulta/consultas/todas",
            "/disponibilidade/**",
            "/enderecos/{usuarioId}",
            "/perfil/voluntario/dados-profissionais",
            "/perfil/voluntario/disponibilidade"
    };

    private static final String[] URLS_ASSISTIDOS = {
            "/consulta/consultas/{idConsulta}/feedbacks",
            "/consulta/**",
            "/consulta/consultas/{id}/feedback",
            "/consulta/consultas/{id}/avaliacao"
    };

    private static final String[] URLS_ASSISTIDOS_E_VOLUNTARIOS = {
            "/agenda/**",
            "/consulta/consultas/minhas",
            "/consulta/consultas/{idUsuario}/proxima"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   AutenticacaoSucessHandler autenticacaoSucessHandler) throws Exception {
        return http
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .cors(Customizer.withDefaults())
                .csrf(CsrfConfigurer<HttpSecurity>::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(URLS_PUBLICAS).permitAll()
                        .requestMatchers(URLS_ADMINISTRADORES).hasRole("ADMINISTRADOR")
                        .requestMatchers(URLS_VOLUNTARIOS).hasRole("VOLUNTARIO")
                        .requestMatchers(URLS_ASSISTIDOS).hasAnyRole("VALOR_SOCIAL", "GRATUIDADE")
                        .requestMatchers(URLS_ASSISTIDOS_E_VOLUNTARIOS).hasAnyRole("VOLUNTARIO", "VALOR_SOCIAL", "GRATUIDADE")
                        .requestMatchers(URLS_INTERNOS).hasAnyRole("ADMINISTRADOR", "VOLUNTARIO")
                        .anyRequest()
                        .authenticated()
                )
                .oauth2Login(oauth2 -> oauth2
                        .successHandler(autenticacaoSucessHandler)
                )
                .exceptionHandling(handling -> handling
                        .authenticationEntryPoint(autenticacaoEntryPoint()))
                .addFilterBefore(jwtAuthenticationFilterBean(), UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http,
                                             PasswordEncoderGateway passwordEncoderGateway) throws Exception {
        AuthenticationManagerBuilder authBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);

        authBuilder.authenticationProvider(
                new AutenticacaoProvider(autenticacaoService(), passwordEncoderGateway)
        );

        return authBuilder.build();
    }

    @Bean
    public AutenticacaoEntryPoint jwtAuthenticationEntryPointBean() {
        return new AutenticacaoEntryPoint();
    }

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.validity}")
    private long jwtTokenValidity;

    @Bean
    public GerenciadorTokenJwtAdapter jwtAuthenticationUtilBean() {
        return new GerenciadorTokenJwtAdapter(secret, jwtTokenValidity);
    }

    @Bean
    public AutenticacaoFilter jwtAuthenticationFilterBean() {
        return new AutenticacaoFilter(autenticacaoService(), jwtAuthenticationUtilBean());
    }

    @Bean
    public SecurityContextRepository securityContextRepository() {
        return new HttpSessionSecurityContextRepository();
    }

    @Bean
    public AutenticacaoSucessHandler autenticacaoSucessHandler(
            CadastrarUsuarioPrimeiraFaseUseCase cadastrarUsuario,
            BuscarUsuarioPorEmailUseCase buscarUsuarioPorEmail,
            GerenciadorTokenJwtAdapter gerenciadorTokenJwtAdapter,
            OAuth2AuthorizedClientService authorizedClientService,
            SecurityContextRepository securityContextRepository,
            AutenticacaoServiceAdapter autenticacaoService) {

        return new AutenticacaoSucessHandler(
                cadastrarUsuario,
                buscarUsuarioPorEmail,
                gerenciadorTokenJwtAdapter,
                authorizedClientService,
                securityContextRepository,
                autenticacaoService
        );
    }

    @Bean
    public AuthenticationEntryPoint autenticacaoEntryPoint() {
        return new AutenticacaoEntryPoint();
    }

    @Bean
    public AutenticacaoServiceAdapter autenticacaoService() {
        return new AutenticacaoServiceAdapter();
    }

    @Bean
    public PasswordEncoderGateway passwordEncoderGateway() {
        return new PasswordEncoderAdapter(new BCryptPasswordEncoder());
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
