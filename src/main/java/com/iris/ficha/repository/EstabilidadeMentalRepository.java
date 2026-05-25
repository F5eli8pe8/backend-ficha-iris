package com.iris.ficha.repository;

import com.iris.ficha.model.EstabilidadeMental;
import java.util.Optional;
import java.util.UUID;

public interface EstabilidadeMentalRepository extends BaseRepository<EstabilidadeMental> {

	Optional<EstabilidadeMental> findByPersonagemId(UUID personagemId);
}
