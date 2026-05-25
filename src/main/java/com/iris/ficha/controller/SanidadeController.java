package com.iris.ficha.controller;

import com.iris.ficha.dto.SanidadeDto;
import com.iris.ficha.service.CrudService;
import com.iris.ficha.service.SanidadeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sanidades")
@RequiredArgsConstructor
public class SanidadeController extends AbstractCrudController<SanidadeDto> {

	private final SanidadeService service;

	@Override
	protected CrudService<SanidadeDto> service() {
		return service;
	}
}
