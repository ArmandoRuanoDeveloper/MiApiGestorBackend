package com.mx.cbtis.gestor.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mx.cbtis.gestor.modelo.Proyectos;
import com.mx.cbtis.gestor.repository.ProyectosRepository;

@ExtendWith(MockitoExtension.class)
class ProyectosControllerTest {

    @Mock
    ProyectosRepository repo;

    @InjectMocks
    ProyectosController controller;

    @Test
    void buscarProyecto_existente() {
        Proyectos p = new Proyectos(1, "Demo", "desc", "2026-01-01", "2026-02-01", "Activo", "azul");
        when(repo.findById(1)).thenReturn(Optional.of(p));

        assertEquals("Demo", controller.getProyecto(1).getNombre());
    }

    @Test
    void buscarProyecto_noExiste_devuelveObjetoVacio() {
        when(repo.findById(99)).thenReturn(Optional.empty());

        assertNull(controller.getProyecto(99).getId_proyecto());
    }

    @Test
    void addProyecto_guardaYDevuelve1() {
        Proyectos p = new Proyectos();

        assertEquals(1, controller.addProyecto(p));
        verify(repo).save(p);
    }

    @Test
    void deleteProyecto_llamaDelete() {
        Proyectos p = new Proyectos();

        assertEquals(1, controller.deleteProyecto(p));
        verify(repo).delete(p);
    }

        @Test
    void addProyecto_conNull_devuelve0() {
        org.junit.jupiter.api.Assertions.assertEquals(0, controller.addProyecto(null));
        org.mockito.Mockito.verifyNoInteractions(repo);
    }

    @Test
    void updateProyecto_conNull_devuelve0() {
        org.junit.jupiter.api.Assertions.assertEquals(0, controller.updateProyecto(null));
        org.mockito.Mockito.verifyNoInteractions(repo);
    }

    @Test
    void deleteProyecto_conNull_devuelve0() {
        org.junit.jupiter.api.Assertions.assertEquals(0, controller.deleteProyecto(null));
        org.mockito.Mockito.verifyNoInteractions(repo);
    }
}