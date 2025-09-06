package org.com.imaapi.infrastructure.controller;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/dev")
@Profile("dev")
@CrossOrigin(origins = "*")
public class DevTokenController {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.validity}")
    private long defaultJwtTokenValidity;

    @PostMapping("/token")
    public ResponseEntity<Map<String, String>> generateDevToken(@RequestBody @Valid DevTokenInput input) {
        long tokenValidity = input.getValidityInSeconds() != null
                ? input.getValidityInSeconds()
                : defaultJwtTokenValidity;

        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

        String authorities = String.join(",", input.getAuthorities());

        String token = Jwts.builder()
                .subject(input.getEmail())
                .claim("authorities", authorities)
                .claim("nome", input.getNome())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + tokenValidity * 1000))
                .signWith(key, Jwts.SIG.HS256)
                .compact();

        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        response.put("type", "Bearer");
        response.put("expiresIn", String.valueOf(tokenValidity));

        return ResponseEntity.ok(response);
    }
}
