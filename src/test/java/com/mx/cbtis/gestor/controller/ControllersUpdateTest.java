package com.mx.cbtis.gestor.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mx.cbtis.gestor.modelo.Asignaciones;
import com.mx.cbtis.gestor.modelo.Comentarios;
import com.mx.cbtis.gestor.modelo.Proyectos;
import com.mx.cbtis.gestor.modelo.Tareas;
import com.mx.cbtis.gestor.repository.AsignacionesRepository;
import com.mx.cbtis.gestor.repository.ComentariosRepository;
import com.mx.cbtis.gestor.repository.ProyectosRepository;
import com.mx.cbtis.gestor.repository.TareasRepository;

@ExtendWith(MockitoExtension.class)
class ControllersUpdateTest {

    @Mock
    ProyectosRepository proyectosRepo;
    @Mock
    AsignacionesRepository asignacionesRepo;
    @Mock
    ComentariosRepository comentariosRepo;
    @Mock
    TareasRepository tareasRepo;

    @InjectMocks
    ProyectosController proyectosController;
    @InjectMocks
    AsignacionesController asignacionesController;
    @InjectMocks
    ComentariosController comentariosController;
    @InjectMocks
    TareasController tareasController;

    @Test
    void updateProyecto_guardaYDevuelve1() {
        Proyectos p = new Proyectos();

        assertEquals(1, proyectosController.updateProyecto(p));
        verify(proyectosRepo).save(p);
    }

    @Test
    void updateAsignacion_guardaYDevuelve1() {
        Asignaciones a = new Asignaciones();

        assertEquals(1, asignacionesController.updateAsignacion(a));
        verify(asignacionesRepo).save(a);
    }

    @Test
    void updateComentario_guardaYDevuelve1() {
        Comentarios c = new Comentarios();

        assertEquals(1, comentariosController.updateComentario(c));
        verify(comentariosRepo).save(c);
    }

    @Test
    void updateTareas_guardaYDevuelve1() {
        Tareas t = new Tareas();

        assertEquals(1, tareasController.updateTareas(t));
        verify(tareasRepo).save(t);
    }
}