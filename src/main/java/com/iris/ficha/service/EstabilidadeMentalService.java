package com.iris.ficha.service;

import com.iris.ficha.dto.EstabilidadeMentalDto;
import com.iris.ficha.exception.ConflictException;
import com.iris.ficha.exception.ResourceNotFoundException;
import com.iris.ficha.model.EstabilidadeMental;
import com.iris.ficha.model.Personagem;
import com.iris.ficha.repository.BaseRepository;
import com.iris.ficha.repository.EstabilidadeMentalRepository;
import com.iris.ficha.repository.PersonagemRepository;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EstabilidadeMentalService extends AbstractCrudService<EstabilidadeMental, EstabilidadeMentalDto> {

	private final EstabilidadeMentalRepository repository;
	private final PersonagemRepository personagemRepository;

	@Override
	protected BaseRepository<EstabilidadeMental> repository() {
		return repository;
	}

	@Override
	protected EstabilidadeMental toEntity(EstabilidadeMentalDto dto) {
		EstabilidadeMental entity = new EstabilidadeMental();
		atualizarEntidade(entity, dto);
		return entity;
	}

	@Override
	protected void atualizarEntidade(EstabilidadeMental entity, EstabilidadeMentalDto dto) {
		Personagem personagem = buscarPersonagem(dto.personagemId());
		validarUnicoPorPersonagem(personagem.getId(), entity.getId());
		entity.setPersonagem(personagem);
		entity.setValorAtual(dto.valorAtual());
		entity.setValorMaximo(dto.valorMaximo());
	}

	@Override
	protected EstabilidadeMentalDto toDto(EstabilidadeMental entity) {
		return new EstabilidadeMentalDto(entity.getId(), entity.getPersonagem().getId(), entity.getValorAtual(), entity.getValorMaximo(), entity.getCriadoEm(), entity.getAtualizadoEm());
	}

	@Override
	protected Specification<EstabilidadeMental> specification(Map<String, String> filtros) {
		ArrayList<Specification<EstabilidadeMental>> specifications = new ArrayList<>();
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
		repository.findByPersonagemId(personagemId).ifPresent(estabilidade -> {
			if (idAtual == null || !estabilidade.getId().equals(idAtual)) {
				throw new ConflictException("Já existe ficha de estabilidade mental para este personagem.");
			}
		});
	}
}
