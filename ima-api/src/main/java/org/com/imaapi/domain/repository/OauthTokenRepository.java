package org.com.imaapi.domain.repository;

import org.springframework.stereotype.Repository;

@Repository
public interface OauthTokenRepository extends JpaRepository<OauthToken, Integer> {
    Optional<OauthToken> findByUsuarioIdUsuario(Integer id);

    boolean existsByUsuarioIdUsuario(Integer id);

    void deleteByUsuarioIdUsuario(Integer id);
}
