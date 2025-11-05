package com.desafio.desafio.service;

import com.desafio.desafio.exception.BusinessException;
import com.desafio.desafio.exception.ResourceNotFoundException;
import com.desafio.desafio.model.Emprestimo;
import com.desafio.desafio.model.Livro;
import com.desafio.desafio.model.StatusEmprestimo;
import com.desafio.desafio.model.Usuario;
import com.desafio.desafio.repository.EmprestimoRepository;
import com.desafio.desafio.repository.LivroRepository;
import com.desafio.desafio.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;

@Service
public class EmprestimoService {

    @Autowired
    private EmprestimoRepository emprestimoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private LivroRepository livroRepository;

    public Emprestimo createEmprestimo(Long usuarioId, Long livroId, LocalDate dataDevolucaoPrevista) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado: " + usuarioId));

        Livro livro = livroRepository.findById(livroId)
                .orElseThrow(() -> new ResourceNotFoundException("Livro não encontrado: " + livroId));

        emprestimoRepository.findByLivroAndStatus(livro, StatusEmprestimo.ATIVO).ifPresent(e -> {
            throw new BusinessException("Livro já está emprestado ativamente.");
        });

        Emprestimo emprestimo = new Emprestimo();
        emprestimo.setUsuario(usuario);
        emprestimo.setLivro(livro);
        emprestimo.setDataEmprestimo(LocalDate.now());
        emprestimo.setDataDevolucao(dataDevolucaoPrevista);
        emprestimo.setStatus(StatusEmprestimo.ATIVO);

        return emprestimoRepository.save(emprestimo);
    }

    public Emprestimo devolverLivro(Long emprestimoId) {
        Emprestimo emprestimo = emprestimoRepository.findById(emprestimoId)
                .orElseThrow(() -> new ResourceNotFoundException("Empréstimo não encontrado: " + emprestimoId));

        if (emprestimo.getStatus() == StatusEmprestimo.DEVOLVIDO) {
            throw new BusinessException("Este livro já foi devolvido.");
        }

        emprestimo.setStatus(StatusEmprestimo.DEVOLVIDO);
        emprestimo.setDataDevolucao(LocalDate.now());

        return emprestimoRepository.save(emprestimo);
    }
}
