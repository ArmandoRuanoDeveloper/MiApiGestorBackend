package com.mx.cbtis.gestor.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mx.cbtis.gestor.modelo.Asignaciones;
import com.mx.cbtis.gestor.repository.AsignacionesRepository;

@ExtendWith(MockitoExtension.class)
class AsignacionesControllerTest {

    @Mock
    AsignacionesRepository repo;

    @InjectMocks
    AsignacionesController controller;

    @Test
    void buscarAsignacion_existente() {
        Asignaciones a = new Asignaciones(1, 1, 1, "2026-01-01");
        when(repo.findById(1)).thenReturn(Optional.of(a));

        assertEquals("2026-01-01", controller.getAsignacion(1).getFecha_asignacion());
    }

    @Test
    void buscarAsignacion_noExiste_devuelveObjetoVacio() {
        when(repo.findById(99)).thenReturn(Optional.empty());

        assertNull(controller.getAsignacion(99).getId_asignacion());
    }

    @Test
    void addAsignacion_guardaYDevuelve1() {
        Asignaciones a = new Asignaciones();

        assertEquals(1, controller.addAsignacion(a));
        verify(repo).save(a);
    }

    @Test
    void deleteAsignacion_llamaDelete() {
        Asignaciones a = new Asignaciones();

        assertEquals(1, controller.deleteAsignacion(a));
        verify(repo).delete(a);
    }
}