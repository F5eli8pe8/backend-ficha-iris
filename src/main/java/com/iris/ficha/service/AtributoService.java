package com.iris.ficha.service;

import com.iris.ficha.dto.AtributoDto;
import com.iris.ficha.exception.ResourceNotFoundException;
import com.iris.ficha.model.Atributo;
import com.iris.ficha.model.Personagem;
import com.iris.ficha.repository.AtributoRepository;
import com.iris.ficha.repository.BaseRepository;
import com.iris.ficha.repository.PersonagemRepository;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AtributoService extends AbstractCrudService<Atributo, AtributoDto> {

	private final AtributoRepository repository;
	private final PersonagemRepository personagemRepository;

	@Override
	protected BaseRepository<Atributo> repository() {
		return repository;
	}

	@Override
	protected Atributo toEntity(AtributoDto dto) {
		Atributo entity = new Atributo();
		atualizarEntidade(entity, dto);
		return entity;
	}

	@Override
	protected void atualizarEntidade(Atributo entity, AtributoDto dto) {
		entity.setPersonagem(buscarPersonagem(dto.personagemId()));
		entity.setNome(dto.nome());
		entity.setValor(dto.valor());
	}

	@Override
	protected AtributoDto toDto(Atributo entity) {
		return new AtributoDto(entity.getId(), entity.getPersonagem().getId(), entity.getNome(), entity.getValor(), entity.getCriadoEm(), entity.getAtualizadoEm());
	}

	@Override
	protected Specification<Atributo> specification(Map<String, String> filtros) {
		ArrayList<Specification<Atributo>> specifications = new ArrayList<>();
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
