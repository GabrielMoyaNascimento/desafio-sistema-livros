package com.desafio.desafio.service;

import com.desafio.desafio.exception.BusinessException;
import com.desafio.desafio.model.Emprestimo;
import com.desafio.desafio.model.Livro;
import com.desafio.desafio.model.StatusEmprestimo;
import com.desafio.desafio.model.Usuario;
import com.desafio.desafio.repository.EmprestimoRepository;
import com.desafio.desafio.repository.LivroRepository;
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
public class EmprestimoServiceTest {

    @Mock private EmprestimoRepository emprestimoRepository;
    @Mock private UsuarioRepository usuarioRepository;
    @Mock private LivroRepository livroRepository;

    @InjectMocks
    private EmprestimoService emprestimoService;

    private Usuario usuario;
    private Livro livro;
    private Emprestimo emprestimo;
    private LocalDate dataDevolucaoPrevista = LocalDate.now().plusDays(14);

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setId(1L);

        livro = new Livro();
        livro.setId(1L);

        emprestimo = new Emprestimo();
        emprestimo.setId(1L);
        emprestimo.setUsuario(usuario);
        emprestimo.setLivro(livro);
        emprestimo.setStatus(StatusEmprestimo.ATIVO);
    }

    @Test
    void testCreateEmprestimo_Success() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(livroRepository.findById(1L)).thenReturn(Optional.of(livro));
        when(emprestimoRepository.findByLivroAndStatus(livro, StatusEmprestimo.ATIVO)).thenReturn(Optional.empty());
        when(emprestimoRepository.save(any(Emprestimo.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Emprestimo novoEmprestimo = emprestimoService.createEmprestimo(1L, 1L, dataDevolucaoPrevista);

        assertNotNull(novoEmprestimo);
        assertEquals(StatusEmprestimo.ATIVO, novoEmprestimo.getStatus());
        assertEquals(livro, novoEmprestimo.getLivro());
        verify(emprestimoRepository, times(1)).save(any(Emprestimo.class));
    }

    @Test
    void testCreateEmprestimo_LivroJaEmprestado() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(livroRepository.findById(1L)).thenReturn(Optional.of(livro));
        when(emprestimoRepository.findByLivroAndStatus(livro, StatusEmprestimo.ATIVO)).thenReturn(Optional.of(emprestimo));

        assertThrows(BusinessException.class, () -> {
            emprestimoService.createEmprestimo(1L, 1L, dataDevolucaoPrevista);
        });

        verify(emprestimoRepository, never()).save(any());
    }

    @Test
    void testDevolverLivro_Success() {
        when(emprestimoRepository.findById(1L)).thenReturn(Optional.of(emprestimo));
        when(emprestimoRepository.save(any(Emprestimo.class))).thenReturn(emprestimo);

        Emprestimo emprestimoDevolvido = emprestimoService.devolverLivro(1L);

        assertEquals(StatusEmprestimo.DEVOLVIDO, emprestimoDevolvido.getStatus());
        assertEquals(LocalDate.now(), emprestimoDevolvido.getDataDevolucao());
        verify(emprestimoRepository, times(1)).save(emprestimo);
    }

    @Test
    void testDevolverLivro_JaDevolvido() {
        emprestimo.setStatus(StatusEmprestimo.DEVOLVIDO);
        when(emprestimoRepository.findById(1L)).thenReturn(Optional.of(emprestimo));

        assertThrows(BusinessException.class, () -> {
            emprestimoService.devolverLivro(1L);
        });

        verify(emprestimoRepository, never()).save(any());
    }
}
