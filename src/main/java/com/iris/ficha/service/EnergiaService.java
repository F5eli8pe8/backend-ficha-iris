package com.iris.ficha.service;

import com.iris.ficha.dto.EnergiaDto;
import com.iris.ficha.exception.ConflictException;
import com.iris.ficha.exception.ResourceNotFoundException;
import com.iris.ficha.model.Energia;
import com.iris.ficha.model.Personagem;
import com.iris.ficha.repository.BaseRepository;
import com.iris.ficha.repository.EnergiaRepository;
import com.iris.ficha.repository.PersonagemRepository;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EnergiaService extends AbstractCrudService<Energia, EnergiaDto> {

	private final EnergiaRepository repository;
	private final PersonagemRepository personagemRepository;

	@Override
	protected BaseRepository<Energia> repository() {
		return repository;
	}

	@Override
	protected Energia toEntity(EnergiaDto dto) {
		Energia entity = new Energia();
		atualizarEntidade(entity, dto);
		return entity;
	}

	@Override
	protected void atualizarEntidade(Energia entity, EnergiaDto dto) {
		Personagem personagem = buscarPersonagem(dto.personagemId());
		validarUnicoPorPersonagem(personagem.getId(), entity.getId());
		entity.setPersonagem(personagem);
		entity.setAtual(dto.atual());
		entity.setMaximo(dto.maximo());
	}

	@Override
	protected EnergiaDto toDto(Energia entity) {
		return new EnergiaDto(entity.getId(), entity.getPersonagem().getId(), entity.getAtual(), entity.getMaximo(), entity.getCriadoEm(), entity.getAtualizadoEm());
	}

	@Override
	protected Specification<Energia> specification(Map<String, String> filtros) {
		ArrayList<Specification<Energia>> specifications = new ArrayList<>();
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

	private void validarUnicoPorPersonagem(UUID personagemId, UUID idAtual) {
		repository.findByPersonagemId(personagemId).ifPresent(energia -> {
			if (idAtual == null || !energia.getId().equals(idAtual)) {
				throw new ConflictException("Já existe ficha de energia para este personagem.");
			}
		});
	}
}
