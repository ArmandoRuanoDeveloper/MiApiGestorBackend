package com.mx.cbtis.gestor.controller;

import static org.hamcrest.Matchers.hasItem;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.mx.cbtis.gestor.modelo.Asignaciones;
import com.mx.cbtis.gestor.repository.AsignacionesRepository;

@SpringBootTest
@AutoConfigureMockMvc
class AsignacionesApiTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    AsignacionesRepository repo;

    @Test
    void addAsignacion_yApareceEnElListado() throws Exception {
        String json = "{\"id_usuario\":1,\"id_tarea\":1,\"fecha_asignacion\":\"2026-03-03\"}";

        mvc.perform(post("/addAsignacion")
                .contentType(MediaType.APPLICATION_JSON).content(json))
           .andExpect(status().isOk())
           .andExpect(content().string("1"));

        mvc.perform(post("/buscarAsignaciones"))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$[*].fecha_asignacion", hasItem("2026-03-03")));
    }

    @Test
    void buscarAsignacion_porId() throws Exception {
        Asignaciones a = repo.save(new Asignaciones(null, 1, 1, "2026-04-04"));

        mvc.perform(get("/buscarAsignacion").param("id", String.valueOf(a.getId_asignacion())))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.fecha_asignacion").value("2026-04-04"));
    }

    @Test
    void deleteAsignacion_laEliminaDeLaBD() throws Exception {
        Asignaciones a = repo.save(new Asignaciones(null, 1, 1, "2026-01-01"));
        String json = "{\"id_asignacion\":" + a.getId_asignacion() + "}";

        mvc.perform(delete("/deleteAsignacion")
                .contentType(MediaType.APPLICATION_JSON).content(json))
           .andExpect(status().isOk())
           .andExpect(content().string("1"));

        assertTrue(repo.findById(a.getId_asignacion()).isEmpty());
    }
}