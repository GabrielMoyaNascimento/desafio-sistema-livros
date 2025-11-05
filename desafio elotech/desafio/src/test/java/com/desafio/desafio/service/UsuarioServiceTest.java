package com.desafio.desafio.service;

import com.desafio.desafio.exception.BusinessException;
import com.desafio.desafio.exception.ResourceNotFoundException;
import com.desafio.desafio.model.Usuario;
import com.desafio.desafio.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNome("João Silva");
        usuario.setEmail("joao@email.com");
        usuario.setDataCadastro(LocalDate.now());
    }

    @Test
    void testGetUsuarioById_Success() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        Usuario encontrado = usuarioService.getUsuarioById(1L);

        assertNotNull(encontrado);
        assertEquals("João Silva", encontrado.getNome());
    }

    @Test
    void testGetUsuarioById_NotFound() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            usuarioService.getUsuarioById(1L);
        });
    }

    @Test
    void testCreateUsuario_Success() {
        when(usuarioRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        Usuario novoUsuario = usuarioService.createUsuario(usuario);

        assertNotNull(novoUsuario);
        assertEquals("joao@email.com", novoUsuario.getEmail());
        verify(usuarioRepository, times(1)).save(usuario);
    }

    @Test
    void testCreateUsuario_EmailAlreadyExists() {
        when(usuarioRepository.findByEmail("joao@email.com")).thenReturn(Optional.of(usuario));

        Usuario usuarioDuplicado = new Usuario();
        usuarioDuplicado.setEmail("joao@email.com");

        assertThrows(BusinessException.class, () -> {
            usuarioService.createUsuario(usuarioDuplicado);
        });

        verify(usuarioRepository, never()).save(any());
    }
}