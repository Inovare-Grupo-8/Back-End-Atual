package org.com.imaapi.infrastructure.controller;

@RestController
@RequestMapping("/email-test")
public class EmailTestController {

    @Autowired
    private EmailServiceImpl emailService;

    @GetMapping("/test-connection")
    public ResponseEntity<String> testEmailConnection(@RequestParam String email) {
        try {
            String resultado = emailService.enviarEmail(email, "Teste", "bem vindo");
            return ResponseEntity.ok("Teste de email: " + resultado);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro no teste: " + e.getMessage());
        }
    }
}
