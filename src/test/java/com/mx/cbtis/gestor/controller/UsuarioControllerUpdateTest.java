package com.mx.cbtis.gestor.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mx.cbtis.gestor.modelo.Usuario;
import com.mx.cbtis.gestor.repository.UsuarioRepository;

@ExtendWith(MockitoExtension.class)
class UsuarioControllerUpdateTest {

    @Mock
    UsuarioRepository repo;

    @InjectMocks
    UsuarioController controller;

    @Test
    void updateUsuario_guardaYDevuelve1() {
        Usuario u = new Usuario(1, "Ana", "ana@mail.com", "1234", "admin", null);

        assertEquals(1, controller.updateUsuario(u));
        verify(repo).save(u);
    }
}