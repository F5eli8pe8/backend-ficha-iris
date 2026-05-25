package com.iris.ficha.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import java.util.UUID;

public record PericiaDto(
	UUID id,
	@NotNull UUID atributoId,
	@NotBlank String nome,
	@NotNull @Min(0) Integer valor,
	Instant criadoEm,
	Instant atualizadoEm
) {
}
