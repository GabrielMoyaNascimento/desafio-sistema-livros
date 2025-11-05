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

    /**
     * Busca todos os livros cadastrados.
     * @return Lista de entidades Livro.
     */
    public List<Livro> getAllLivros() {
        return livroRepository.findAll();
    }

    /**
     * Busca um livro específico pelo seu ID.
     * @param id O ID do livro.
     * @return A entidade Livro.
     * @throws ResourceNotFoundException se o livro não for encontrado.
     */
    public Livro getLivroById(Long id) {
        return livroRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Livro não encontrado com id: " + id));
    }

    /**
     * Cria um novo livro, validando se o ISBN já existe.
     * @param livro A entidade Livro (vinda do DTO).
     * @return O Livro salvo com ID.
     * @throws BusinessException se o ISBN já estiver cadastrado.
     */
    @Transactional
    public Livro createLivro(Livro livro) {
        // Validação: "isbn" deve ser único (conforme modelagem)
        livroRepository.findByIsbn(livro.getIsbn()).ifPresent(l -> {
            throw new BusinessException("ISBN já cadastrado: " + livro.getIsbn());
        });

        // As validações de @NotBlank, @NotNull, etc. (do DTO)
        // são tratadas pelo Spring @Valid no Controller.
        return livroRepository.save(livro);
    }

    /**
     * Atualiza um livro existente.
     * @param id O ID do livro a ser atualizado.
     * @param livroDetails A entidade com os novos dados.
     * @return O Livro atualizado.
     * @throws ResourceNotFoundException se o livro não for encontrado.
     * @throws BusinessException se o novo ISBN já pertencer a outro livro.
     */
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

    /**
     * Exclui um livro, se ele não possuir empréstimos ativos.
     * @param id O ID do livro a ser excluído.
     * @throws ResourceNotFoundException se o livro não for encontrado.
     * @throws BusinessException se o livro estiver atualmente emprestado.
     */
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