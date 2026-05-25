package com.iris.ficha.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import java.util.UUID;

public record AtributoDto(
	UUID id,
	@NotNull UUID personagemId,
	@NotBlank String nome,
	@NotNull @Min(0) Integer valor,
	Instant criadoEm,
	Instant atualizadoEm
) {
}
