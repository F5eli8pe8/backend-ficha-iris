package com.iris.ficha.service;

import com.iris.ficha.dto.PoderDto;
import com.iris.ficha.exception.ResourceNotFoundException;
import com.iris.ficha.model.Personagem;
import com.iris.ficha.model.Poder;
import com.iris.ficha.repository.BaseRepository;
import com.iris.ficha.repository.PersonagemRepository;
import com.iris.ficha.repository.PoderRepository;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PoderService extends AbstractCrudService<Poder, PoderDto> {

	private final PoderRepository repository;
	private final PersonagemRepository personagemRepository;

	@Override
	protected BaseRepository<Poder> repository() {
		return repository;
	}

	@Override
	protected Poder toEntity(PoderDto dto) {
		Poder entity = new Poder();
		atualizarEntidade(entity, dto);
		return entity;
	}

	@Override
	protected void atualizarEntidade(Poder entity, PoderDto dto) {
		entity.setPersonagem(buscarPersonagem(dto.personagemId()));
		entity.setNome(dto.nome());
		entity.setEntidade(dto.entidade());
		entity.setDescricao(dto.descricao());
		entity.setCustoEnergia(dto.custoEnergia());
	}

	@Override
	protected PoderDto toDto(Poder entity) {
		return new PoderDto(entity.getId(), entity.getPersonagem().getId(), entity.getNome(), entity.getEntidade(), entity.getDescricao(), entity.getCustoEnergia(), entity.getCriadoEm(), entity.getAtualizadoEm());
	}

	@Override
	protected Specification<Poder> specification(Map<String, String> filtros) {
		ArrayList<Specification<Poder>> specifications = new ArrayList<>();
		specifications.add(SpecificationUtils.containsIgnoreCase("nome", filtros.get("nome")));
		String personagemId = filtros.get("personagemId");
		if (SpecificationUtils.hasText(personagemId)) {
			UUID parsed = UUID.fromString(personagemId);
			specifications.add((root, query, cb) -> cb.equal(root.get("personagem").get("id"), parsed));
		}
		return SpecificationUtils.andAll(specifications);
	}

	private Personagem buscarPersonagem(UUID personagemId) {
		return personagemRepository.findById(personagemId)
				.orElseThrow(() -> new ResourceNotFoundException("Personagem não encontrado: " + personagemId));
	}
}
