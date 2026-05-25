package com.iris.ficha.controller;

import com.iris.ficha.dto.AtributoDto;
import com.iris.ficha.service.AtributoService;
import com.iris.ficha.service.CrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/atributos")
@RequiredArgsConstructor
public class AtributoController extends AbstractCrudController<AtributoDto> {

	private final AtributoService service;

	@Override
	protected CrudService<AtributoDto> service() {
		return service;
	}
}
