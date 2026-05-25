package com.iris.ficha.controller;

import com.iris.ficha.dto.PericiaDto;
import com.iris.ficha.service.CrudService;
import com.iris.ficha.service.PericiaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pericias")
@RequiredArgsConstructor
public class PericiaController extends AbstractCrudController<PericiaDto> {

	private final PericiaService service;

	@Override
	protected CrudService<PericiaDto> service() {
		return service;
	}
}
