package org.com.imaapi.infrastructure.gateway;

import org.com.imaapi.domain.gateway.PasswordEncoderGateway;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class LoggingPasswordEncoder implements PasswordEncoderGateway {

    private final PasswordEncoder delegate;
    
    public LoggingPasswordEncoder(PasswordEncoder delegate) {
        this.delegate = delegate;
    }
    
    @Override
    public String encode(String rawPassword) {
        return delegate.encode(rawPassword);
    }
    
    @Override
    public boolean matches(String rawPassword, String encodedPassword) {
        return delegate.matches(rawPassword, encodedPassword);
    }
}
