package com.iris.ficha.controller;

import com.iris.ficha.dto.RitualDto;
import com.iris.ficha.service.CrudService;
import com.iris.ficha.service.RitualService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rituais")
@RequiredArgsConstructor
public class RitualController extends AbstractCrudController<RitualDto> {

	private final RitualService service;

	@Override
	protected CrudService<RitualDto> service() {
		return service;
	}
}
