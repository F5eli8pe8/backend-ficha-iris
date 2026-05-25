package com.iris.ficha.controller;

import com.iris.ficha.dto.PersonagemDto;
import com.iris.ficha.security.JwtUtil;
import com.iris.ficha.service.CrudService;
import com.iris.ficha.service.PersonagemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/personagens")
@RequiredArgsConstructor
public class PersonagemController extends AbstractCrudController<PersonagemDto> {

    private final PersonagemService service;
    private final JwtUtil jwtUtil;

    @Override
    protected CrudService<PersonagemDto> service() {
        return service;
    }

    @Override
    @GetMapping
    public Page<PersonagemDto> listar(@RequestParam Map<String, String> filtros,
                                      Pageable pageable,
                                      @RequestHeader("Authorization") String cabecalho) {
        String token = cabecalho.substring(7);
        String funcao = jwtUtil.extrairFuncao(token);

        if (!funcao.equalsIgnoreCase("mestre")) {
            UUID usuarioId = jwtUtil.extrairId(token);
            filtros.put("usuarioId", usuarioId.toString());
        }

        return service.listar(filtros, pageable);
    }

    @Override
    @GetMapping("/{id}")
    public PersonagemDto buscarPorId(@PathVariable UUID id,
                                     @RequestHeader("Authorization") String cabecalho) {
        String token = cabecalho.substring(7);
        String funcao = jwtUtil.extrairFuncao(token);
        PersonagemDto personagem = service.buscarPorId(id);

        if (!funcao.equalsIgnoreCase("mestre")) {
            UUID usuarioId = jwtUtil.extrairId(token);
            if (!personagem.usuarioId().equals(usuarioId)) {
                throw new AccessDeniedException("Acesso negado a este personagem");
            }
        }

        return personagem;
    }

    @Override
    @PutMapping("/{id}")
    public PersonagemDto atualizar(@PathVariable UUID id,
                                   @Valid @RequestBody PersonagemDto dto,
                                   @RequestHeader("Authorization") String cabecalho) {
        String token = cabecalho.substring(7);
        String funcao = jwtUtil.extrairFuncao(token);
        PersonagemDto personagem = service.buscarPorId(id);

        if (!funcao.equalsIgnoreCase("mestre")) {
            UUID usuarioId = jwtUtil.extrairId(token);
            if (!personagem.usuarioId().equals(usuarioId)) {
                throw new AccessDeniedException("Acesso negado a este personagem");
            }
        }

        return service.atualizar(id, dto);
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable UUID id,
                        @RequestHeader("Authorization") String cabecalho) {
        String token = cabecalho.substring(7);
        String funcao = jwtUtil.extrairFuncao(token);
        PersonagemDto personagem = service.buscarPorId(id);

        if (!funcao.equalsIgnoreCase("mestre")) {
            UUID usuarioId = jwtUtil.extrairId(token);
            if (!personagem.usuarioId().equals(usuarioId)) {
                throw new AccessDeniedException("Acesso negado a este personagem");
            }
        }

        service.deletar(id);
    }
}