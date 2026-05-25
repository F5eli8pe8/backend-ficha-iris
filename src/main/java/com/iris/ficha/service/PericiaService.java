package com.iris.ficha.service;

import com.iris.ficha.dto.PericiaDto;
import com.iris.ficha.exception.ResourceNotFoundException;
import com.iris.ficha.model.Atributo;
import com.iris.ficha.model.Pericia;
import com.iris.ficha.repository.AtributoRepository;
import com.iris.ficha.repository.BaseRepository;
import com.iris.ficha.repository.PericiaRepository;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PericiaService extends AbstractCrudService<Pericia, PericiaDto> {

	private final PericiaRepository repository;
	private final AtributoRepository atributoRepository;

	@Override
	protected BaseRepository<Pericia> repository() {
		return repository;
	}

	@Override
	protected Pericia toEntity(PericiaDto dto) {
		Pericia entity = new Pericia();
		atualizarEntidade(entity, dto);
		return entity;
	}

	@Override
	protected void atualizarEntidade(Pericia entity, PericiaDto dto) {
		entity.setAtributo(buscarAtributo(dto.atributoId()));
		entity.setNome(dto.nome());
		entity.setValor(dto.valor());
	}

	@Override
	protected PericiaDto toDto(Pericia entity) {
		return new PericiaDto(entity.getId(), entity.getAtributo().getId(), entity.getNome(), entity.getValor(), entity.getCriadoEm(), entity.getAtualizadoEm());
	}

	@Override
	protected Specification<Pericia> specification(Map<String, String> filtros) {
		ArrayList<Specification<Pericia>> specifications = new ArrayList<>();
		specifications.add(SpecificationUtils.containsIgnoreCase("nome", filtros.get("nome")));
		String atributoId = filtros.get("atributoId");
		if (SpecificationUtils.hasText(atributoId)) {
			UUID parsed = UUID.fromString(atributoId);
			specifications.add((root, query, cb) -> cb.equal(root.get("atributo").get("id"), parsed));
		}
		return SpecificationUtils.andAll(specifications);
	}

	private Atributo buscarAtributo(UUID atributoId) {
		return atributoRepository.findById(atributoId)
				.orElseThrow(() -> new ResourceNotFoundException("Atributo não encontrado: " + atributoId));
	}
}
