package com.iris.ficha.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import java.util.UUID;

public record PersonagemDto(
	UUID id,
	@NotNull UUID usuarioId,
	@NotBlank String nome,
	@NotBlank String raca,
	@NotBlank String arquetipo,
	String imagemUrl,
	Integer pontosConhecimento,
	String anotacoes,
	Instant criadoEm,
	Instant atualizadoEm
) {
}
