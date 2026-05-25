package com.iris.ficha.service;

import com.iris.ficha.dto.ItemDto;
import com.iris.ficha.exception.ResourceNotFoundException;
import com.iris.ficha.model.Item;
import com.iris.ficha.model.Personagem;
import com.iris.ficha.repository.BaseRepository;
import com.iris.ficha.repository.ItemRepository;
import com.iris.ficha.repository.PersonagemRepository;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemService extends AbstractCrudService<Item, ItemDto> {

	private final ItemRepository repository;
	private final PersonagemRepository personagemRepository;

	@Override
	protected BaseRepository<Item> repository() {
		return repository;
	}

	@Override
	protected Item toEntity(ItemDto dto) {
		Item entity = new Item();
		atualizarEntidade(entity, dto);
		return entity;
	}

	@Override
	protected void atualizarEntidade(Item entity, ItemDto dto) {
		entity.setPersonagem(buscarPersonagem(dto.personagemId()));
		entity.setNome(dto.nome());
		entity.setDescricao(dto.descricao());
		entity.setQuantidade(dto.quantidade());
	}

	@Override
	protected ItemDto toDto(Item entity) {
		return new ItemDto(entity.getId(), entity.getPersonagem().getId(), entity.getNome(), entity.getDescricao(), entity.getQuantidade(), entity.getCriadoEm(), entity.getAtualizadoEm());
	}

	@Override
	protected Specification<Item> specification(Map<String, String> filtros) {
		ArrayList<Specification<Item>> specifications = new ArrayList<>();
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
