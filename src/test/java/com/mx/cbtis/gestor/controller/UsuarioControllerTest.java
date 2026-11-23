package com.mx.cbtis.gestor.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mx.cbtis.gestor.modelo.Usuario;
import com.mx.cbtis.gestor.repository.UsuarioRepository;

@ExtendWith(MockitoExtension.class)
class UsuarioControllerTest {

    @Mock
    UsuarioRepository repo;

    @InjectMocks
    UsuarioController controller;

    @Test
    void loginCorrecto_devuelveElUsuario() {
        Usuario u = new Usuario(1, "Ana", "ana@mail.com", "1234", "admin", null);
        when(repo.findByCorreoPass("ana@mail.com", "1234")).thenReturn(Optional.of(u));

        Usuario param = new Usuario();
        param.setCorreo_electronico("ana@mail.com");
        param.setContrasena("1234");

        assertEquals("Ana", controller.getUsuarioLogin(param).getNombre());
    }

    @Test
    void loginIncorrecto_devuelveNombre000() {
        when(repo.findByCorreoPass("ana@mail.com", "mala")).thenReturn(Optional.empty());

        Usuario param = new Usuario();
        param.setCorreo_electronico("ana@mail.com");
        param.setContrasena("mala");

        assertEquals("000", controller.getUsuarioLogin(param).getNombre());
    }

    @Test
    void buscarUsuario_existente() {
        Usuario u = new Usuario(1, "Ana", "ana@mail.com", "1234", "admin", null);
        when(repo.findById(1)).thenReturn(Optional.of(u));

        assertEquals("Ana", controller.getUsuario(1).getNombre());
    }

    @Test
    void addUsuario_guardaYDevuelve1() {
        Usuario u = new Usuario();

        assertEquals(1, controller.addUsuario(u));
        verify(repo).save(u);
    }

    @Test
    void deleteUsuario_llamaDelete() {
        Usuario u = new Usuario();

        assertEquals(1, controller.deleteUsuario(u));
        verify(repo).delete(u);
    }

        @Test
    void addUsuario_conNull_devuelve0() {
        assertEquals(0, controller.addUsuario(null));
        verifyNoInteractions(repo);
    }

    @Test
    void updateUsuario_conNull_devuelve0() {
        assertEquals(0, controller.updateUsuario(null));
        verifyNoInteractions(repo);
    }

    @Test
    void deleteUsuario_conNull_devuelve0() {
        assertEquals(0, controller.deleteUsuario(null));
        verifyNoInteractions(repo);
    }
}