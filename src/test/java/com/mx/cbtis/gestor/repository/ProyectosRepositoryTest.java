package com.mx.cbtis.gestor.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.mx.cbtis.gestor.modelo.Proyectos;

@DataJpaTest
class ProtectosRepositoryTest {

    @Autowired
    ProyectosRepository repo;

    @Test
    void guardaYConsultaProyecto() {
        Proyectos p = new Proyectos(null, "Demo", "desc", "2026-01-01", "2026-02-01", "Activo", "azul");

        Proyectos guardado = repo.save(p);

        assertNotNull(guardado.getId_proyecto());
        assertEquals("Demo", repo.findById(guardado.getId_proyecto()).get().getNombre());
    }

    @Test
    void actualizaProyecto() {
        Proyectos p = repo.save(new Proyectos(null, "Demo", "desc", "2026-01-01", "2026-02-01", "Activo", "azul"));

        p.setEstado_proyecto("Terminado");
        repo.save(p);

        assertEquals("Terminado", repo.findById(p.getId_proyecto()).get().getEstado_proyecto());
    }

    @Test
    void eliminaProyecto() {
        Proyectos p = repo.save(new Proyectos(null, "Demo", "desc", "2026-01-01", "2026-02-01", "Activo", "azul"));

        repo.deleteById(p.getId_proyecto());

        assertTrue(repo.findById(p.getId_proyecto()).isEmpty());
    }
}