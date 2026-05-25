package com.iris.ficha.controller;

import com.iris.ficha.dto.EstabilidadeMentalDto;
import com.iris.ficha.service.CrudService;
import com.iris.ficha.service.EstabilidadeMentalService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/estabilidades-mentais")
@RequiredArgsConstructor
public class EstabilidadeMentalController extends AbstractCrudController<EstabilidadeMentalDto> {

	private final EstabilidadeMentalService service;

	@Override
	protected CrudService<EstabilidadeMentalDto> service() {
		return service;
	}
}
