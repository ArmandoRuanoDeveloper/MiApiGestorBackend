package com.mx.cbtis.gestor.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.mx.cbtis.gestor.modelo.Comentarios;

@DataJpaTest
class ComentariosRepositoryTest {

    @Autowired
    ComentariosRepository repo;

    @Test
    void guardaYConsultaComentario() {
        Comentarios guardado = repo.save(new Comentarios(null, "1", 5, "Hola", "2026-01-01"));

        assertNotNull(guardado.getId_comentario());
        assertEquals("Hola", repo.findById(guardado.getId_comentario()).get().getTexto_comentario());
    }

    @Test
    void findByTarea_filtraSoloLasDeLaTarea() {
        repo.save(new Comentarios(null, "1", 5, "De la tarea 5", "2026-01-01"));
        repo.save(new Comentarios(null, "1", 6, "De la tarea 6", "2026-01-01"));

        assertEquals(1, repo.findByTarea(5).size());
    }

    @Test
    void eliminaComentario() {
        Comentarios c = repo.save(new Comentarios(null, "1", 5, "Hola", "2026-01-01"));

        repo.deleteById(c.getId_comentario());

        assertTrue(repo.findById(c.getId_comentario()).isEmpty());
    }
}