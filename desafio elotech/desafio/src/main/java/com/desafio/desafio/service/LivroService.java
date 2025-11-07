package com.desafio.desafio.service;

import com.desafio.desafio.exception.BusinessException;
import com.desafio.desafio.exception.ResourceNotFoundException;
import com.desafio.desafio.model.Livro;
import com.desafio.desafio.model.StatusEmprestimo;
import com.desafio.desafio.repository.EmprestimoRepository;
import com.desafio.desafio.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class LivroService {

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private EmprestimoRepository emprestimoRepository;

    public List<Livro> getAllLivros() {
        return livroRepository.findAll();
    }

    public Livro getLivroById(Long id) {
        return livroRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Livro não encontrado com id: " + id));
    }

    @Transactional
    public Livro createLivro(Livro livro) {
        livroRepository.findByIsbn(livro.getIsbn()).ifPresent(l -> {
            throw new BusinessException("ISBN já cadastrado: " + livro.getIsbn());
        });

        return livroRepository.save(livro);
    }

    @Transactional
    public Livro updateLivro(Long id, Livro livroDetails) {
        Livro livroExistente = getLivroById(id);

        if (!livroExistente.getIsbn().equals(livroDetails.getIsbn())) {
            Optional<Livro> livroComNovoIsbn = livroRepository.findByIsbn(livroDetails.getIsbn());

            if (livroComNovoIsbn.isPresent() && !livroComNovoIsbn.get().getId().equals(id)) {
                throw new BusinessException("O ISBN " + livroDetails.getIsbn() + " já pertence a outro livro.");
            }
        }

        livroExistente.setTitulo(livroDetails.getTitulo());
        livroExistente.setAutor(livroDetails.getAutor());
        livroExistente.setIsbn(livroDetails.getIsbn());
        livroExistente.setDataPublicacao(livroDetails.getDataPublicacao());
        livroExistente.setCategoria(livroDetails.getCategoria());

        return livroRepository.save(livroExistente);
    }

    @Transactional
    public void deleteLivro(Long id) {
        Livro livro = getLivroById(id);

        // Regra de Negócio: Não permitir exclusão de livro com empréstimo ATIVO.
        // Requisito: "Um livro pode ser emprestado várias vezes,
        // mas apenas um empréstimo ativo por vez."
        emprestimoRepository.findByLivroAndStatus(livro, StatusEmprestimo.ATIVO)
                .ifPresent(e -> {
                    throw new BusinessException("Não é possível excluir o livro. Ele está atualmente emprestado pelo usuário ID: " + e.getUsuario().getId());
                });

        livroRepository.delete(livro);
    }
}