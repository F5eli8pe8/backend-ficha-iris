package com.iris.ficha.service;

import com.iris.ficha.dto.HabilidadeDto;
import com.iris.ficha.exception.ResourceNotFoundException;
import com.iris.ficha.model.Habilidade;
import com.iris.ficha.model.Personagem;
import com.iris.ficha.repository.BaseRepository;
import com.iris.ficha.repository.HabilidadeRepository;
import com.iris.ficha.repository.PersonagemRepository;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HabilidadeService extends AbstractCrudService<Habilidade, HabilidadeDto> {

	private final HabilidadeRepository repository;
	private final PersonagemRepository personagemRepository;

	@Override
	protected BaseRepository<Habilidade> repository() {
		return repository;
	}

	@Override
	protected Habilidade toEntity(HabilidadeDto dto) {
		Habilidade entity = new Habilidade();
		atualizarEntidade(entity, dto);
		return entity;
	}

	@Override
	protected void atualizarEntidade(Habilidade entity, HabilidadeDto dto) {
		entity.setPersonagem(buscarPersonagem(dto.personagemId()));
		entity.setNome(dto.nome());
		entity.setDescricao(dto.descricao());
	}

	@Override
	protected HabilidadeDto toDto(Habilidade entity) {
		return new HabilidadeDto(entity.getId(), entity.getPersonagem().getId(), entity.getNome(), entity.getDescricao(), entity.getCriadoEm(), entity.getAtualizadoEm());
	}

	@Override
	protected Specification<Habilidade> specification(Map<String, String> filtros) {
		ArrayList<Specification<Habilidade>> specifications = new ArrayList<>();
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
