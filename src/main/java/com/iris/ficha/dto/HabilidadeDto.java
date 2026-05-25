package com.iris.ficha.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import java.util.UUID;

public record HabilidadeDto(
	UUID id,
	@NotNull UUID personagemId,
	@NotBlank String nome,
	String descricao,
	Instant criadoEm,
	Instant atualizadoEm
) {
}
