package com.mx.cbtis.gestor.controller;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.mx.cbtis.gestor.modelo.Tareas;
import com.mx.cbtis.gestor.repository.TareasRepository;

@SpringBootTest
@AutoConfigureMockMvc
class TareasApiTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    TareasRepository repo;

    @Test
    void addTareas_yApareceEnElListado() throws Exception {
        String json = "{\"nombre\":\"TareaApi\",\"estado\":\"Pendiente\",\"id_proyecto\":1}";

        mvc.perform(post("/addTareas")
                .contentType(MediaType.APPLICATION_JSON).content(json))
           .andExpect(status().isOk())
           .andExpect(content().string("1"));

        mvc.perform(post("/buscarTareas"))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$[*].nombre", hasItem("TareaApi")));
    }

    @Test
    void buscarTarea_porId() throws Exception {
        Tareas t = repo.save(new Tareas(null, "Buscada", "desc", "2026-01-01", "Por hacer", "Pendiente", 1));

        mvc.perform(get("/buscarTarea").param("id_tarea", String.valueOf(t.getId_tarea())))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.nombre").value("Buscada"));
    }

    @Test
    void getTarea_filtraPorProyecto() throws Exception {
        repo.save(new Tareas(null, "DelProyecto777", "desc", "2026-01-01", "Por hacer", "Pendiente", 777));
        repo.save(new Tareas(null, "OtroProyecto", "desc", "2026-01-01", "Por hacer", "Pendiente", 888));

        mvc.perform(post("/getTarea")
                .contentType(MediaType.APPLICATION_JSON).content("{\"id_proyecto\":777}"))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$", hasSize(1)))
           .andExpect(jsonPath("$[0].nombre").value("DelProyecto777"));
    }

    @Test
    void deleteTareas_laEliminaDeLaBD() throws Exception {
        Tareas t = repo.save(new Tareas(null, "Borrar", "desc", "2026-01-01", "Por hacer", "Pendiente", 1));
        String json = "{\"id_tarea\":" + t.getId_tarea() + "}";

        mvc.perform(delete("/deleteTareas")
                .contentType(MediaType.APPLICATION_JSON).content(json))
           .andExpect(status().isOk())
           .andExpect(content().string("1"));

        assertTrue(repo.findById(t.getId_tarea()).isEmpty());
    }
}