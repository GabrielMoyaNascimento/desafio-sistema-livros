package com.desafio.desafio.dto;

import com.desafio.desafio.model.Livro;

import java.time.LocalDate;

/**
 */
public record LivroResponseDTO(
        Long id,
        String titulo,
        String autor,
        String isbn,
        LocalDate dataPublicacao,
        String categoria
) {
    /**
     * Método de fábrica para converter a entidade JPA (Livro)
     * neste DTO (LivroResponseDTO).
     *
     * @param livro A entidade vinda do banco de dados.
     * @return O DTO preenchido para resposta.
     */
    public static LivroResponseDTO fromEntity(Livro livro) {
        return new LivroResponseDTO(
                livro.getId(),
                livro.getTitulo(),
                livro.getAutor(),
                livro.getIsbn(),
                livro.getDataPublicacao(),
                livro.getCategoria()
        );
    }
}