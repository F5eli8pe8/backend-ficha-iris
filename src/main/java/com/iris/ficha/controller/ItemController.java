package com.iris.ficha.controller;

import com.iris.ficha.dto.ItemDto;
import com.iris.ficha.service.CrudService;
import com.iris.ficha.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/itens")
@RequiredArgsConstructor
public class ItemController extends AbstractCrudController<ItemDto> {

	private final ItemService service;

	@Override
	protected CrudService<ItemDto> service() {
		return service;
	}
}
