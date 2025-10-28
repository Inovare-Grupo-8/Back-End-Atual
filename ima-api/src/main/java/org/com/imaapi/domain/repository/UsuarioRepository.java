package org.com.imaapi.domain.repository;

import org.com.imaapi.domain.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByEmail(String email);

    @Query("SELECT u FROM Usuario u WHERE LOWER(u.ficha.nome) LIKE LOWER(CONCAT('%', :nome, '%'))")
    List<Usuario> findByFichaNomeContainingIgnoreCase(@Param("nome") String nome);

    @Query("SELECT u.ficha.idFicha FROM Usuario u WHERE u.idUsuario = :id")
    Integer findFichaIdByUsuarioId(@Param("id") Integer id);
}