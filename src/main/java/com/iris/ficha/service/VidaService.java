package com.iris.ficha.service;

import com.iris.ficha.dto.VidaDto;
import com.iris.ficha.exception.ConflictException;
import com.iris.ficha.exception.ResourceNotFoundException;
import com.iris.ficha.model.Personagem;
import com.iris.ficha.model.Vida;
import com.iris.ficha.repository.BaseRepository;
import com.iris.ficha.repository.PersonagemRepository;
import com.iris.ficha.repository.VidaRepository;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VidaService extends AbstractCrudService<Vida, VidaDto> {

	private final VidaRepository repository;
	private final PersonagemRepository personagemRepository;

	@Override
	protected BaseRepository<Vida> repository() {
		return repository;
	}

	@Override
	protected Vida toEntity(VidaDto dto) {
		Vida entity = new Vida();
		atualizarEntidade(entity, dto);
		return entity;
	}

	@Override
	protected void atualizarEntidade(Vida entity, VidaDto dto) {
		Personagem personagem = buscarPersonagem(dto.personagemId());
		validarUnicoPorPersonagem(personagem.getId(), entity.getId());
		entity.setPersonagem(personagem);
		entity.setAtual(dto.atual());
		entity.setMaximo(dto.maximo());
	}

	@Override
	protected VidaDto toDto(Vida entity) {
		return new VidaDto(entity.getId(), entity.getPersonagem().getId(), entity.getAtual(), entity.getMaximo(), entity.getCriadoEm(), entity.getAtualizadoEm());
	}

	@Override
	protected Specification<Vida> specification(Map<String, String> filtros) {
		ArrayList<Specification<Vida>> specifications = new ArrayList<>();
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
		repository.findByPersonagemId(personagemId).ifPresent(vida -> {
			if (idAtual == null || !vida.getId().equals(idAtual)) {
				throw new ConflictException("Já existe ficha de vida para este personagem.");
			}
		});
	}
}
