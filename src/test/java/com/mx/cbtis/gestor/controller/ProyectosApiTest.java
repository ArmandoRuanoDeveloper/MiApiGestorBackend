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

import com.mx.cbtis.gestor.modelo.Proyectos;
import com.mx.cbtis.gestor.repository.ProyectosRepository;

@SpringBootTest
@AutoConfigureMockMvc
class ProyectosApiTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    ProyectosRepository repo;

    @Test
    void addProyecto_yApareceEnElListado() throws Exception {
        String json = "{\"nombre\":\"ProyectoApi\",\"estado_proyecto\":\"Activo\"}";

        mvc.perform(post("/addProyecto")
                .contentType(MediaType.APPLICATION_JSON).content(json))
           .andExpect(status().isOk())
           .andExpect(content().string("1"));

        mvc.perform(post("/buscarProyectos"))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$[*].nombre", hasItem("ProyectoApi")));
    }

    @Test
    void buscarProyecto_porId() throws Exception {
        Proyectos p = repo.save(new Proyectos(null, "Buscado", "desc", "2026-01-01", "2026-02-01", "Activo", "azul"));

        mvc.perform(get("/buscarProyecto").param("id_proyecto", String.valueOf(p.getId_proyecto())))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.nombre").value("Buscado"));
    }

    @Test
    void deleteProyecto_loEliminaDeLaBD() throws Exception {
        Proyectos p = repo.save(new Proyectos(null, "Borrar", "desc", "2026-01-01", "2026-02-01", "Activo", "azul"));
        String json = "{\"id_proyecto\":" + p.getId_proyecto() + "}";

        mvc.perform(delete("/deleteProyecto")
                .contentType(MediaType.APPLICATION_JSON).content(json))
           .andExpect(status().isOk())
           .andExpect(content().string("1"));

        assertTrue(repo.findById(p.getId_proyecto()).isEmpty());
    }
}