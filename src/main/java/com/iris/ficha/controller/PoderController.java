package com.iris.ficha.controller;

import com.iris.ficha.dto.PoderDto;
import com.iris.ficha.service.CrudService;
import com.iris.ficha.service.PoderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/poderes")
@RequiredArgsConstructor
public class PoderController extends AbstractCrudController<PoderDto> {

	private final PoderService service;

	@Override
	protected CrudService<PoderDto> service() {
		return service;
	}
}
