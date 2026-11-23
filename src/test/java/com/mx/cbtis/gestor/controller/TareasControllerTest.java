package com.mx.cbtis.gestor.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mx.cbtis.gestor.modelo.Tareas;
import com.mx.cbtis.gestor.repository.TareasRepository;

@ExtendWith(MockitoExtension.class)
class TareasControllerTest {
    
    @Mock
    TareasRepository repo;
    
    @InjectMocks
    TareasController controller;

    @Test
    void buscarTarea_existente() {
        Tareas t = new Tareas(1, "T1", "desc", "2026-01-01", "Por hacer", "Pendiente", 1);
        when(repo.findById(1)).thenReturn(Optional.of(t));

        assertEquals("T1", controller.getTarea(1).getNombre());
    }

    @Test
    void buscarTarea_noExiste_devuelveObjetoVacio() {
        when(repo.findById(99)).thenReturn(Optional.empty());

        assertNull(controller.getTarea(99).getId_tarea());
    }

    @Test 
    void addTarea_guardaYDevuelve1() {
        Tareas t = new Tareas();

        assertEquals(1, controller.addTareas(t));
        verify(repo).save(t);
    }

    @Test 
    void deleteTarea_llamaDelete() {
        Tareas t = new Tareas();

        assertEquals(1, controller.deleteTareas(t));
        verify(repo).delete(t);
    }

        @Test
    void addTareas_conNull_devuelve0() {
        org.junit.jupiter.api.Assertions.assertEquals(0, controller.addTareas(null));
        org.mockito.Mockito.verifyNoInteractions(repo);
    }

    @Test
    void updateTareas_conNull_devuelve0() {
        org.junit.jupiter.api.Assertions.assertEquals(0, controller.updateTareas(null));
        org.mockito.Mockito.verifyNoInteractions(repo);
    }

    @Test
    void deleteTareas_conNull_devuelve0() {
        org.junit.jupiter.api.Assertions.assertEquals(0, controller.deleteTareas(null));
        org.mockito.Mockito.verifyNoInteractions(repo);
    }
}