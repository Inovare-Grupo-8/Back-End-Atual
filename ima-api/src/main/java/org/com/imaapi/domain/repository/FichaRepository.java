package org.com.imaapi.domain.repository;

import org.com.imaapi.domain.model.Ficha;
import org.com.imaapi.domain.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FichaRepository extends JpaRepository<Ficha, Integer> {
    @Query(value = "SELECT u.* FROM usuario u " +
           "JOIN ficha f ON f.id_ficha = u.fk_ficha " +
           "WHERE LOWER(CONCAT(f.nome, ' ', f.sobrenome)) LIKE LOWER(CONCAT('%', :termo, '%'))", 
           nativeQuery = true)
    List<Usuario> findByNomeOrSobrenomeContaining(@Param("termo") String termo);
    
    Optional<Ficha> findByCpf(String cpf);
}
