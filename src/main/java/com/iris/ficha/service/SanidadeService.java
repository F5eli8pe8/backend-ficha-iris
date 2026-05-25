package com.iris.ficha.service;

import com.iris.ficha.dto.SanidadeDto;
import com.iris.ficha.exception.ConflictException;
import com.iris.ficha.exception.ResourceNotFoundException;
import com.iris.ficha.model.Personagem;
import com.iris.ficha.model.Sanidade;
import com.iris.ficha.repository.BaseRepository;
import com.iris.ficha.repository.PersonagemRepository;
import com.iris.ficha.repository.SanidadeRepository;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SanidadeService extends AbstractCrudService<Sanidade, SanidadeDto> {

	private final SanidadeRepository repository;
	private final PersonagemRepository personagemRepository;

	@Override
	protected BaseRepository<Sanidade> repository() {
		return repository;
	}

	@Override
	protected Sanidade toEntity(SanidadeDto dto) {
		Sanidade entity = new Sanidade();
		atualizarEntidade(entity, dto);
		return entity;
	}

	@Override
	protected void atualizarEntidade(Sanidade entity, SanidadeDto dto) {
		Personagem personagem = buscarPersonagem(dto.personagemId());
		validarUnicoPorPersonagem(personagem.getId(), entity.getId());
		entity.setPersonagem(personagem);
		entity.setAtual(dto.atual());
		entity.setMaximo(dto.maximo());
	}

	@Override
	protected SanidadeDto toDto(Sanidade entity) {
		return new SanidadeDto(entity.getId(), entity.getPersonagem().getId(), entity.getAtual(), entity.getMaximo(), entity.getCriadoEm(), entity.getAtualizadoEm());
	}

	@Override
	protected Specification<Sanidade> specification(Map<String, String> filtros) {
		ArrayList<Specification<Sanidade>> specifications = new ArrayList<>();
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
		repository.findByPersonagemId(personagemId).ifPresent(sanidade -> {
			if (idAtual == null || !sanidade.getId().equals(idAtual)) {
				throw new ConflictException("Já existe ficha de sanidade para este personagem.");
			}
		});
	}
}
