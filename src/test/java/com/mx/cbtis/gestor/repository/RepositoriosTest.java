package com.mx.cbtis.gestor.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.mx.cbtis.gestor.modelo.Tareas;
import com.mx.cbtis.gestor.modelo.Usuario;

@DataJpaTest
class RepositoriosTest {

    @Autowired TareasRepository tareas;
    @Autowired UsuarioRepository usuarios;

    @Test
    void findByIdProyecto_filtraSoloLasDelProyecto() {
        tareas.save(new Tareas(null, "T1", "d", "2026-01-01", "Por hacer", "Pendiente", 1));
        tareas.save(new Tareas(null, "T2", "d", "2026-01-01", "Por hacer", "Pendiente", 2));

        assertEquals(1, tareas.findByIdProyecto(1).size());
    }

    @Test
    void findByCorreoPass_loginCorrectoEIncorrecto() {
        usuarios.save(new Usuario(null, "Ana", "ana@mail.com", "1234", "admin", null));

        assertTrue(usuarios.findByCorreoPass("ana@mail.com", "1234").isPresent());
        assertTrue(usuarios.findByCorreoPass("ana@mail.com", "mala").isEmpty());
    }
}