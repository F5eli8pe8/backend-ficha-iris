package com.iris.ficha.repository;

import com.iris.ficha.model.Sanidade;
import java.util.Optional;
import java.util.UUID;

public interface SanidadeRepository extends BaseRepository<Sanidade> {

	Optional<Sanidade> findByPersonagemId(UUID personagemId);
}
