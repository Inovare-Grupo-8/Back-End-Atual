package org.com.imaapi.domain.gateway;

public interface PasswordEncoderGateway {
    String encode(String rawPassword);
    boolean matches(String rawPassword, String encodedPassword);
}
