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

import com.mx.cbtis.gestor.modelo.Comentarios;
import com.mx.cbtis.gestor.repository.ComentariosRepository;

@SpringBootTest
@AutoConfigureMockMvc
class ComentariosApiTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    ComentariosRepository repo;

    @Test
    void addComentario_yApareceEnElListado() throws Exception {
        String json = "{\"id_usuario\":\"1\",\"id_tarea\":5,\"texto_comentario\":\"ComentarioApi\",\"fecha_hora\":\"2026-01-01\"}";

        mvc.perform(post("/addComentario")
                .contentType(MediaType.APPLICATION_JSON).content(json))
           .andExpect(status().isOk())
           .andExpect(content().string("1"));

        mvc.perform(post("/buscarComentarios"))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$[*].texto_comentario", hasItem("ComentarioApi")));
    }

    @Test
    void buscarComentario_porId() throws Exception {
        Comentarios c = repo.save(new Comentarios(null, "1", 5, "Buscado", "2026-01-01"));

        mvc.perform(get("/buscarComentario").param("id", String.valueOf(c.getId_comentario())))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.texto_comentario").value("Buscado"));
    }

    @Test
    void getComentario_filtraPorTarea() throws Exception {
        repo.save(new Comentarios(null, "1", 555, "DeLaTarea555", "2026-01-01"));
        repo.save(new Comentarios(null, "1", 666, "DeOtraTarea", "2026-01-01"));

        mvc.perform(post("/getComentario")
                .contentType(MediaType.APPLICATION_JSON).content("{\"id_tarea\":555}"))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$", hasSize(1)))
           .andExpect(jsonPath("$[0].texto_comentario").value("DeLaTarea555"));
    }

    @Test
    void deleteComentario_loEliminaDeLaBD() throws Exception {
        Comentarios c = repo.save(new Comentarios(null, "1", 5, "Borrar", "2026-01-01"));
        String json = "{\"id_comentario\":" + c.getId_comentario() + "}";

        mvc.perform(delete("/deleteComentario")
                .contentType(MediaType.APPLICATION_JSON).content(json))
           .andExpect(status().isOk())
           .andExpect(content().string("1"));

        assertTrue(repo.findById(c.getId_comentario()).isEmpty());
    }
}