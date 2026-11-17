package com.mx.cbtis.gestor.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.mx.cbtis.gestor.modelo.Usuario;

@DataJpaTest
class UsuarioRepositoryTest {

    @Autowired
    UsuarioRepository repo;

    @Test
    void guardaYConsultaUsuario() {
        Usuario guardado = repo.save(new Usuario(null, "Ana", "ana@mail.com", "1234", "admin", null));

        assertNotNull(guardado.getId_usuario());
        assertEquals("Ana", repo.findById(guardado.getId_usuario()).get().getNombre());
    }

    @Test
    void findByCorreoPass_correcto() {
        repo.save(new Usuario(null, "Ana", "ana@mail.com", "1234", "admin", null));

        assertTrue(repo.findByCorreoPass("ana@mail.com", "1234").isPresent());
    }

    @Test
    void findByCorreoPass_incorrecto() {
        repo.save(new Usuario(null, "Ana", "ana@mail.com", "1234", "admin", null));

        assertTrue(repo.findByCorreoPass("ana@mail.com", "mala").isEmpty());
    }

    @Test
    void eliminaUsuario() {
        Usuario u = repo.save(new Usuario(null, "Ana", "ana@mail.com", "1234", "admin", null));

        repo.deleteById(u.getId_usuario());

        assertTrue(repo.findById(u.getId_usuario()).isEmpty());
    }
}