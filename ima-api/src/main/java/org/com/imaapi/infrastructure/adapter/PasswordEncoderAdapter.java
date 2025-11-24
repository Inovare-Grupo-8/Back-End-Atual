package org.com.imaapi.infrastructure.adapter;

import org.com.imaapi.domain.gateway.PasswordEncoderGateway;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordEncoderAdapter implements PasswordEncoder {
    private final PasswordEncoderGateway passwordEncoderGateway;

    public PasswordEncoderAdapter(PasswordEncoderGateway passwordEncoderGateway) {
        this.passwordEncoderGateway = passwordEncoderGateway;
    }


    @Override
    public String encode(CharSequence rawPassword) {
        return passwordEncoderGateway.encode(rawPassword.toString());
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return passwordEncoderGateway.matches(rawPassword.toString(), encodedPassword);
    }
}
