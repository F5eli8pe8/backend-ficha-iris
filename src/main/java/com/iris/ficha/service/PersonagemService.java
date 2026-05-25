package com.iris.ficha.service;

import com.iris.ficha.dto.PersonagemDto;
import com.iris.ficha.exception.ResourceNotFoundException;
import com.iris.ficha.model.Personagem;
import com.iris.ficha.model.Usuario;
import com.iris.ficha.repository.BaseRepository;
import com.iris.ficha.repository.PersonagemRepository;
import com.iris.ficha.repository.UsuarioRepository;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersonagemService extends AbstractCrudService<Personagem, PersonagemDto> {

	private final PersonagemRepository repository;
	private final UsuarioRepository usuarioRepository;

	@Override
	protected BaseRepository<Personagem> repository() {
		return repository;
	}

	@Override
	protected Personagem toEntity(PersonagemDto dto) {
		Personagem entity = new Personagem();
		atualizarEntidade(entity, dto);
		return entity;
	}

	@Override
	protected void atualizarEntidade(Personagem entity, PersonagemDto dto) {
		entity.setUsuario(buscarUsuario(dto.usuarioId()));
		entity.setNome(dto.nome());
		entity.setRaca(dto.raca());
		entity.setArquetipo(dto.arquetipo());
		entity.setImagemUrl(dto.imagemUrl());
		entity.setPontosConhecimento(dto.pontosConhecimento() == null ? 0 : dto.pontosConhecimento());
		entity.setAnotacoes(dto.anotacoes());
	}

	@Override
	protected PersonagemDto toDto(Personagem entity) {
		return new PersonagemDto(entity.getId(), entity.getUsuario().getId(), entity.getNome(), entity.getRaca(), entity.getArquetipo(), entity.getImagemUrl(), entity.getPontosConhecimento(), entity.getAnotacoes(), entity.getCriadoEm(), entity.getAtualizadoEm());
	}

	@Override
	protected Specification<Personagem> specification(Map<String, String> filtros) {
		ArrayList<Specification<Personagem>> specifications = new ArrayList<>();
		specifications.add(SpecificationUtils.containsIgnoreCase("nome", filtros.get("nome")));
		specifications.add(SpecificationUtils.containsIgnoreCase("raca", filtros.get("raca")));
		specifications.add(SpecificationUtils.containsIgnoreCase("arquetipo", filtros.get("arquetipo")));
		String usuarioId = filtros.get("usuarioId");
		if (SpecificationUtils.hasText(usuarioId)) {
			UUID parsed = UUID.fromString(usuarioId);
			specifications.add((root, query, cb) -> cb.equal(root.get("usuario").get("id"), parsed));
		}
		return SpecificationUtils.andAll(specifications);
	}

	private Usuario buscarUsuario(UUID usuarioId) {
		return usuarioRepository.findById(usuarioId)
				.orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado: " + usuarioId));
	}
}
