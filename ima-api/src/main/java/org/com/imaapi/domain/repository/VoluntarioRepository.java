package org.com.imaapi.domain.repository;

import org.com.imaapi.domain.model.Voluntario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoluntarioRepository extends JpaRepository<Voluntario, Integer> {
    Voluntario findByUsuario_IdUsuario(Integer usuarioId);
    List<Voluntario> findByFuncao(String funcao);
}