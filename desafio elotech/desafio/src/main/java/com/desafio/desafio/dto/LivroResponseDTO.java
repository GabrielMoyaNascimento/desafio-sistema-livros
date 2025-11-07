package com.desafio.desafio.dto;

import com.desafio.desafio.model.Livro;
import com.desafio.desafio.model.Emprestimo;
import com.desafio.desafio.model.StatusEmprestimo;
import java.time.LocalDate;
import java.util.Optional;

/**
 */
public record LivroResponseDTO(
        Long id,
        String titulo,
        String autor,
        String isbn,
        LocalDate dataPublicacao,
        String categoria,
        String status,
        Long activeEmprestimoId
) {
    /**
     * Método de fábrica para converter a entidade JPA (Livro)
     * neste DTO (LivroResponseDTO).
     *
     * @param livro A entidade vinda do banco de dados.
     * @return O DTO preenchido para resposta.
     */
    public static LivroResponseDTO fromEntity(Livro livro) {
        Optional<Emprestimo> emprestimoAtivoOpt = livro.getEmprestimos().stream()
                .filter(e -> e.getStatus() == StatusEmprestimo.ATIVO)
                .findFirst();

        String status = emprestimoAtivoOpt.isPresent() ? "EMPRESTADO" : "DISPONÍVEL";
        Long emprestimoId = emprestimoAtivoOpt.map(Emprestimo::getId).orElse(null);

        return new LivroResponseDTO(
                livro.getId(),
                livro.getTitulo(),
                livro.getAutor(),
                livro.getIsbn(),
                livro.getDataPublicacao(),
                livro.getCategoria(),
                status,
                emprestimoId
        );
    }
}