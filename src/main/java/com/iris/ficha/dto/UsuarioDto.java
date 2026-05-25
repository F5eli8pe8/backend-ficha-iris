package com.iris.ficha.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.time.Instant;
import java.util.UUID;

public record UsuarioDto(
	UUID id,
	@NotBlank String nomeUsuario,
	@NotBlank @Email String email,
	@NotBlank String senhaHash,
	@NotBlank String funcao,
	Instant criadoEm,
	Instant atualizadoEm
) {
}
