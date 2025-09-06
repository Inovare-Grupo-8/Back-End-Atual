package org.com.imaapi.domain.repository;

import org.com.imaapi.domain.model.Telefone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TelefoneRepository extends JpaRepository<Telefone, Integer> {
    void deleteByFichaIdFicha(Integer idFicha);
    List<Telefone> findByFichaIdFicha(Integer idFicha);
}
