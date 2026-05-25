package com.iris.ficha.controller;

import com.iris.ficha.dto.UsuarioDto;
import com.iris.ficha.service.CrudService;
import com.iris.ficha.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController extends AbstractCrudController<UsuarioDto> {

	private final UsuarioService service;

	@Override
	protected CrudService<UsuarioDto> service() {
		return service;
	}
}
