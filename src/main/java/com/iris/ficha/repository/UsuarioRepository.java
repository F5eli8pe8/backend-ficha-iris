package com.iris.ficha.repository;

import com.iris.ficha.model.Usuario;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends BaseRepository<Usuario> {
    Optional<Usuario> findByEmail(String email);
}