package org.com.imaapi.repository;

import org.com.imaapi.domain.model.usuario.Voluntario;
import org.com.imaapi.domain.model.usuario.VoluntarioEspecialidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoluntarioEspecialidadeRepository extends JpaRepository<VoluntarioEspecialidade, Integer> {
    void deleteByVoluntario(Voluntario voluntario);
}
