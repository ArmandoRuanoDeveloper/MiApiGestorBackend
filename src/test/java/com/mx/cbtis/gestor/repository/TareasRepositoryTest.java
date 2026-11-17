package com.mx.cbtis.gestor.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.mx.cbtis.gestor.modelo.Tareas;

@DataJpaTest 
class TareasRepositoryTest {

    @Autowired 
    TareasRepository repo;

    @Test 
    void guardaYConsultaTarea() {
        Tareas t = new Tareas(null, "Tarea1", "desc", "2026-01-01", "Por hacer", "Pendiente", 1);

        Tareas guardada = repo.save(t);

        assertNotNull(guardada.getId_tarea());
        assertEquals("Tarea1", repo.findById(guardada.getId_tarea()).get().getNombre());
    }

    @Test 
    void actualizaTarea() {
        Tareas t = repo.save(new Tareas(null, "Tarea1", "desc", "2026-01-01", "Por hacer", "Pendiente", 1));

        t.setEstado("Completada");
        repo.save(t);

        assertEquals("Completada", repo.findById(t.getId_tarea()).get().getEstado());
    }

    @Test 
    void eliminaTarea() {
        Tareas t = repo.save(new Tareas(null, "Tarea1", "desc", "2026-01-01", "Por hacer", "Pendiente", 1));

        repo.deleteById(t.getId_tarea());

        assertTrue(repo.findById(t.getId_tarea()).isEmpty());
    }
}