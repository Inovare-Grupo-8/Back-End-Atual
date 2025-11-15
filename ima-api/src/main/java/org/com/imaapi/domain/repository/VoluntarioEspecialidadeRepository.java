package org.com.imaapi.domain.repository;

import org.com.imaapi.domain.model.Voluntario;
import org.com.imaapi.domain.model.VoluntarioEspecialidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoluntarioEspecialidadeRepository extends JpaRepository<VoluntarioEspecialidade, Integer> {
    void deleteByVoluntario(Voluntario voluntario);
}
