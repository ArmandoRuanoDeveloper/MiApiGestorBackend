package com.mx.cbtis.gestor.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mx.cbtis.gestor.modelo.Comentarios;
import com.mx.cbtis.gestor.repository.ComentariosRepository;

@ExtendWith(MockitoExtension.class)
class ComentariosControllerTest {

    @Mock
    ComentariosRepository repo;

    @InjectMocks
    ComentariosController controller;

    @Test
    void getComentario_porTarea() {
        Comentarios c = new Comentarios(1, "1", 5, "Hola", "2026-01-01");
        when(repo.findByTarea(5)).thenReturn(List.of(c));

        Comentarios param = new Comentarios();
        param.setId_tarea(5);

        assertEquals(1, controller.getComentario(param).size());
    }

    @Test
    void buscarComentario_existente() {
        Comentarios c = new Comentarios(1, "1", 5, "Hola", "2026-01-01");
        when(repo.findById(1)).thenReturn(Optional.of(c));

        assertEquals("Hola", controller.getComentario(1).getTexto_comentario());
    }

    @Test
    void addComentario_guardaYDevuelve1() {
        Comentarios c = new Comentarios();

        assertEquals(1, controller.addComentario(c));
        verify(repo).save(c);
    }

    @Test
    void deleteComentario_llamaDelete() {
        Comentarios c = new Comentarios();

        assertEquals(1, controller.deleteComentario(c));
        verify(repo).delete(c);
    }
}