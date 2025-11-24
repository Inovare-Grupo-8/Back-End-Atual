package org.com.imaapi.infrastructure.adapter;

import org.com.imaapi.domain.gateway.PasswordEncoderGateway;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordEncoderAdapter implements PasswordEncoderGateway {
    private final PasswordEncoder passwordEncoder;

    public PasswordEncoderAdapter(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public String encode(String rawPassword) {
        return passwordEncoder.encode(rawPassword.toString());
    }

    @Override
    public boolean matches(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword.toString(), encodedPassword);
    }
}
