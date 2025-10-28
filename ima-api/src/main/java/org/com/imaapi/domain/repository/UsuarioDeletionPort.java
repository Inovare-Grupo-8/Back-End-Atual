package org.com.imaapi.domain.repository;

public interface UsuarioDeletionPort {
    /**
     * Delete a user and related rows in a safe order to avoid FK issues.
     * @param usuarioId 
     */
    void deleteByIdCascade(Integer usuarioId);
}
