package com.iris.ficha.controller;

import com.iris.ficha.dto.VidaDto;
import com.iris.ficha.service.CrudService;
import com.iris.ficha.service.VidaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/vidas")
@RequiredArgsConstructor
public class VidaController extends AbstractCrudController<VidaDto> {

	private final VidaService service;

	@Override
	protected CrudService<VidaDto> service() {
		return service;
	}
}
