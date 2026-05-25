package com.iris.ficha.controller;

import com.iris.ficha.service.CrudService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

public abstract class AbstractCrudController<DTO> {

    protected abstract CrudService<DTO> service();

    @GetMapping
    public Page<DTO> listar(@RequestParam Map<String, String> filtros,
                            Pageable pageable,
                            @RequestHeader(value = "Authorization", required = false) String cabecalho) {
        return service().listar(filtros, pageable);
    }

    @GetMapping("/{id}")
    public DTO buscarPorId(@PathVariable UUID id,
                           @RequestHeader(value = "Authorization", required = false) String cabecalho) {
        return service().buscarPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DTO criar(@Valid @RequestBody DTO dto) {
        return service().criar(dto);
    }

    @PutMapping("/{id}")
    public DTO atualizar(@PathVariable UUID id,
                         @Valid @RequestBody DTO dto,
                         @RequestHeader(value = "Authorization", required = false) String cabecalho) {
        return service().atualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable UUID id,
                        @RequestHeader(value = "Authorization", required = false) String cabecalho) {
        service().deletar(id);
    }
}