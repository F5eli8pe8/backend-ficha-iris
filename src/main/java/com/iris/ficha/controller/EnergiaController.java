package com.iris.ficha.controller;

import com.iris.ficha.dto.EnergiaDto;
import com.iris.ficha.service.CrudService;
import com.iris.ficha.service.EnergiaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/energias")
@RequiredArgsConstructor
public class EnergiaController extends AbstractCrudController<EnergiaDto> {

	private final EnergiaService service;

	@Override
	protected CrudService<EnergiaDto> service() {
		return service;
	}
}
