package com.iris.ficha.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import java.util.UUID;

public record EstabilidadeMentalDto(
	UUID id,
	@NotNull UUID personagemId,
	@NotNull @Min(0) Integer valorAtual,
	@NotNull @Min(0) Integer valorMaximo,
	Instant criadoEm,
	Instant atualizadoEm
) {
}
