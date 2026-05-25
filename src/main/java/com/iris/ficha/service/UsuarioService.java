package com.iris.ficha.service;

import com.iris.ficha.dto.UsuarioDto;
import com.iris.ficha.model.Usuario;
import com.iris.ficha.repository.BaseRepository;
import com.iris.ficha.repository.UsuarioRepository;
import java.util.ArrayList;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService extends AbstractCrudService<Usuario, UsuarioDto> {

	private final UsuarioRepository repository;
	private final PasswordEncoder passwordEncoder;


	@Override
	protected BaseRepository<Usuario> repository() {
		return repository;
	}

	@Override
	protected Usuario toEntity(UsuarioDto dto) {
		Usuario entity = new Usuario();
		atualizarEntidade(entity, dto);
		return entity;
	}


	@Override
	protected void atualizarEntidade(Usuario entity, UsuarioDto dto) {
    	entity.setNomeUsuario(dto.nomeUsuario());
    	entity.setEmail(dto.email());
    	entity.setSenhaHash(passwordEncoder.encode(dto.senhaHash()));
   		entity.setFuncao("player");
	}

	@Override
	protected UsuarioDto toDto(Usuario entity) {
		return new UsuarioDto(entity.getId(), entity.getNomeUsuario(), entity.getEmail(), entity.getSenhaHash(), entity.getFuncao(), entity.getCriadoEm(), entity.getAtualizadoEm());
	}

	@Override
	protected Specification<Usuario> specification(Map<String, String> filtros) {
		ArrayList<Specification<Usuario>> specifications = new ArrayList<>();
		specifications.add(SpecificationUtils.containsIgnoreCase("nomeUsuario", filtros.get("nomeUsuario")));
		specifications.add(SpecificationUtils.containsIgnoreCase("email", filtros.get("email")));
		specifications.add(SpecificationUtils.containsIgnoreCase("funcao", filtros.get("funcao")));
		return SpecificationUtils.andAll(specifications);
	}
}
