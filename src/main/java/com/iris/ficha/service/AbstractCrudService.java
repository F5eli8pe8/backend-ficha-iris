package com.iris.ficha.service;

import com.iris.ficha.exception.ResourceNotFoundException;
import com.iris.ficha.repository.BaseRepository;
import java.util.Map;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public abstract class AbstractCrudService<E, DTO> implements CrudService<DTO> {

	protected abstract BaseRepository<E> repository();

	protected abstract E toEntity(DTO dto);

	protected abstract void atualizarEntidade(E entity, DTO dto);

	protected abstract DTO toDto(E entity);

	protected abstract org.springframework.data.jpa.domain.Specification<E> specification(Map<String, String> filtros);

	@Override
	public Page<DTO> listar(Map<String, String> filtros, Pageable pageable) {
		org.springframework.data.jpa.domain.Specification<E> spec = specification(filtros);
		Page<E> page = spec == null ? repository().findAll(pageable) : repository().findAll(spec, pageable);
		return page.map(this::toDto);
	}

	@Override
	public DTO buscarPorId(UUID id) {
		return toDto(obterEntidade(id));
	}

	@Override
	public DTO criar(DTO dto) {
		return toDto(repository().save(toEntity(dto)));
	}

	@Override
	public DTO atualizar(UUID id, DTO dto) {
		E entity = obterEntidade(id);
		atualizarEntidade(entity, dto);
		return toDto(repository().save(entity));
	}

	@Override
	public void deletar(UUID id) {
		repository().delete(obterEntidade(id));
	}

	protected E obterEntidade(UUID id) {
		return repository().findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Recurso não encontrado: " + id));
	}
}
