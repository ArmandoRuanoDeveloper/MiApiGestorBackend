package com.mx.cbtis.gestor.modelo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ModeloSettersTest {

    @Test
    void tareas_guardaDescripcionFechaYLista() {
        Tareas t = new Tareas();
        t.setDescripcion("Revisar entrega");
        t.setFecha_vencimiento("2024-06-13");
        t.setLista("terminado");

        assertEquals("Revisar entrega", t.getDescripcion());
        assertEquals("2024-06-13", t.getFecha_vencimiento());
        assertEquals("terminado", t.getLista());
    }

    @Test
    void proyectos_guardaDescripcionFechasYFondo() {
        Proyectos p = new Proyectos();
        p.setDescripcion("API Rest");
        p.setFecha_inicio("2024-05-31");
        p.setFecha_finalizacion("2024-06-29");
        p.setFondo("/Images/fondo.gif");

        assertEquals("API Rest", p.getDescripcion());
        assertEquals("2024-05-31", p.getFecha_inicio());
        assertEquals("2024-06-29", p.getFecha_finalizacion());
        assertEquals("/Images/fondo.gif", p.getFondo());
    }

    @Test
    void usuario_guardaImagen() {
        Usuario u = new Usuario();
        u.setImagen_usuario("#70FAB7");

        assertEquals("#70FAB7", u.getImagen_usuario());
    }
}