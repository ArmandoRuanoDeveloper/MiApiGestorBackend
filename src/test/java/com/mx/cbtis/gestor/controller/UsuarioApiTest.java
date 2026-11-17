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

import com.mx.cbtis.gestor.modelo.Usuario;
import com.mx.cbtis.gestor.repository.UsuarioRepository;

@SpringBootTest
@AutoConfigureMockMvc
class UsuarioApiTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    UsuarioRepository repo;

    @Test
    void addUsuario_yApareceEnElListado() throws Exception {
        String json = "{\"nombre\":\"UsuarioApi\",\"correo_electronico\":\"api@mail.com\",\"contrasena\":\"1234\",\"rol\":\"admin\"}";

        mvc.perform(post("/addUsuario")
                .contentType(MediaType.APPLICATION_JSON).content(json))
           .andExpect(status().isOk())
           .andExpect(content().string("1"));

        mvc.perform(post("/buscarUsuarios"))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$[*].nombre", hasItem("UsuarioApi")));
    }

    @Test
    void buscarUsuario_porId() throws Exception {
        Usuario u = repo.save(new Usuario(null, "Buscado", "buscado@mail.com", "1234", "admin", null));

        mvc.perform(get("/buscarUsuario").param("id", String.valueOf(u.getId_usuario())))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.nombre").value("Buscado"));
    }

    @Test
    void login_correcto() throws Exception {
        repo.save(new Usuario(null, "Ana", "login1@mail.com", "1234", "admin", null));
        String json = "{\"correo_electronico\":\"login1@mail.com\",\"contrasena\":\"1234\"}";

        mvc.perform(post("/getUsuarioLogin")
                .contentType(MediaType.APPLICATION_JSON).content(json))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.nombre").value("Ana"));
    }

    @Test
    void login_incorrecto() throws Exception {
        repo.save(new Usuario(null, "Ana", "login2@mail.com", "1234", "admin", null));
        String json = "{\"correo_electronico\":\"login2@mail.com\",\"contrasena\":\"mala\"}";

        mvc.perform(post("/getUsuarioLogin")
                .contentType(MediaType.APPLICATION_JSON).content(json))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.nombre").value("000"));
    }

    @Test
    void deleteUsuario_loEliminaDeLaBD() throws Exception {
        Usuario u = repo.save(new Usuario(null, "Borrar", "borrar@mail.com", "1234", "admin", null));
        String json = "{\"id_usuario\":" + u.getId_usuario() + "}";

        mvc.perform(delete("/deleteUsuario")
                .contentType(MediaType.APPLICATION_JSON).content(json))
           .andExpect(status().isOk())
           .andExpect(content().string("1"));

        assertTrue(repo.findById(u.getId_usuario()).isEmpty());
    }
}