package com.mx.cbtis.gestor.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.mx.cbtis.gestor.modelo.Asignaciones;

@DataJpaTest
class AsignacionesRepositoryTest {

    @Autowired
    AsignacionesRepository repo;

    @Test
    void guardaYConsultaAsignacion() {
        Asignaciones guardada = repo.save(new Asignaciones(null, 1, 1, "2026-01-01"));

        assertNotNull(guardada.getId_asignacion());
        assertEquals("2026-01-01", repo.findById(guardada.getId_asignacion()).get().getFecha_asignacion());
    }

    @Test
    void actualizaAsignacion() {
        Asignaciones a = repo.save(new Asignaciones(null, 1, 1, "2026-01-01"));

        a.setFecha_asignacion("2026-05-05");
        repo.save(a);

        assertEquals("2026-05-05", repo.findById(a.getId_asignacion()).get().getFecha_asignacion());
    }

    @Test
    void eliminaAsignacion() {
        Asignaciones a = repo.save(new Asignaciones(null, 1, 1, "2026-01-01"));

        repo.deleteById(a.getId_asignacion());

        assertTrue(repo.findById(a.getId_asignacion()).isEmpty());
    }
}