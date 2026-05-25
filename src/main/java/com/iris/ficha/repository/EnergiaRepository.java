package com.iris.ficha.repository;

import com.iris.ficha.model.Energia;
import java.util.Optional;
import java.util.UUID;

public interface EnergiaRepository extends BaseRepository<Energia> {

	Optional<Energia> findByPersonagemId(UUID personagemId);
}
