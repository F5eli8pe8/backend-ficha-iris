package com.iris.ficha.repository;

import com.iris.ficha.model.Vida;
import java.util.Optional;
import java.util.UUID;

public interface VidaRepository extends BaseRepository<Vida> {

	Optional<Vida> findByPersonagemId(UUID personagemId);
}
