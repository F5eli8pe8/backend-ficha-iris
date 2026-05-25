package com.iris.ficha.controller;

import com.iris.ficha.dto.HabilidadeDto;
import com.iris.ficha.service.CrudService;
import com.iris.ficha.service.HabilidadeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/habilidades")
@RequiredArgsConstructor
public class HabilidadeController extends AbstractCrudController<HabilidadeDto> {

	private final HabilidadeService service;

	@Override
	protected CrudService<HabilidadeDto> service() {
		return service;
	}
}
