package com.iris.ficha.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import java.util.UUID;

public record EnergiaDto(
	UUID id,
	@NotNull UUID personagemId,
	@NotNull @Min(0) Integer atual,
	@NotNull @Min(0) Integer maximo,
	Instant criadoEm,
	Instant atualizadoEm
) {
}
