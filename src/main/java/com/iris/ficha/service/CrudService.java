package com.iris.ficha.service;

import java.util.Map;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CrudService<DTO> {

	Page<DTO> listar(Map<String, String> filtros, Pageable pageable);

	DTO buscarPorId(UUID id);

	DTO criar(DTO dto);

	DTO atualizar(UUID id, DTO dto);

	void deletar(UUID id);
}
