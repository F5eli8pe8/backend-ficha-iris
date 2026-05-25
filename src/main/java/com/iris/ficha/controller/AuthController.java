package com.iris.ficha.controller;

import com.iris.ficha.dto.LoginDto;
import com.iris.ficha.dto.TokenDto;
import com.iris.ficha.exception.ResourceNotFoundException;
import com.iris.ficha.model.Usuario;
import com.iris.ficha.repository.UsuarioRepository;
import com.iris.ficha.security.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public TokenDto login(@Valid @RequestBody LoginDto dto) {

        // Busca o usuário pelo email
        Usuario usuario = usuarioRepository.findByEmail(dto.email())
                .orElseThrow(() -> new ResourceNotFoundException("Email ou senha inválidos"));

        // Verifica se a senha enviada bate com o hash salvo no banco
        if (!passwordEncoder.matches(dto.senha(), usuario.getSenhaHash())) {
            throw new ResourceNotFoundException("Email ou senha inválidos");
        }

        // Gera e retorna o token JWT
        String token = jwtUtil.gerarToken(usuario.getId(), usuario.getEmail(), usuario.getFuncao());
        return new TokenDto(token, usuario.getFuncao());
    }
}