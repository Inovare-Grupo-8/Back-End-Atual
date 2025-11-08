package org.com.imaapi.domain.repository;

import org.com.imaapi.domain.model.Voluntario;
import org.com.imaapi.domain.model.VoluntarioEspecialidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoluntarioEspecialidadeRepository extends JpaRepository<VoluntarioEspecialidade, Integer> {
    void deleteByVoluntario(Voluntario voluntario);
    List<VoluntarioEspecialidade> findByVoluntario(Voluntario voluntario);
}
