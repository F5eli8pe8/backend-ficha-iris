package com.iris.ficha.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import java.util.UUID;

public record PoderDto(
	UUID id,
	@NotNull UUID personagemId,
	@NotBlank String nome,
	@NotBlank String entidade,
	String descricao,
	@NotNull @Min(0) Integer custoEnergia,
	Instant criadoEm,
	Instant atualizadoEm
) {
}
