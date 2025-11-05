package com.desafio.desafio.dto;

import com.desafio.desafio.model.Emprestimo;
import com.desafio.desafio.model.Livro;
import com.desafio.desafio.model.StatusEmprestimo;
import com.desafio.desafio.model.Usuario;

import java.time.LocalDate;

/**
 * DTO de Resposta (Saída) para a entidade Empréstimo.
 * Ele inclui DTOs aninhados e simplificados de Usuário e Livro
 * para evitar referências cíclicas na serialização JSON.
 */
public record EmprestimoResponseDTO(
        Long id,
        LocalDate dataEmprestimo,
        LocalDate dataDevolucao, // Esta será a data prevista (se ATIVO) ou real (se DEVOLVIDO)
        StatusEmprestimo status,
        UsuarioSimplificadoDTO usuario,
        LivroSimplificadoDTO livro
) {
    /**
     * Método de fábrica para converter a entidade JPA (Emprestimo)
     * neste DTO (EmprestimoResponseDTO).
     *
     * @param emprestimo A entidade vinda do banco de dados.
     * @return O DTO preenchido com os dados aninhados.
     */
    public static EmprestimoResponseDTO fromEntity(Emprestimo emprestimo) {
        return new EmprestimoResponseDTO(
                emprestimo.getId(),
                emprestimo.getDataEmprestimo(),
                emprestimo.getDataDevolucao(),
                emprestimo.getStatus(),
                UsuarioSimplificadoDTO.fromEntity(emprestimo.getUsuario()),
                LivroSimplificadoDTO.fromEntity(emprestimo.getLivro())
        );
    }

    /**
     * DTO interno e simplificado para representar o Usuário
     * dentro do Empréstimo.
     */
    public record UsuarioSimplificadoDTO(Long id, String nome, String email) {
        public static UsuarioSimplificadoDTO fromEntity(Usuario usuario) {
            return new UsuarioSimplificadoDTO(usuario.getId(), usuario.getNome(), usuario.getEmail());
        }
    }

    /**
     * DTO interno e simplificado para representar o Livro
     * dentro do Empréstimo.
     */
    public record LivroSimplificadoDTO(Long id, String titulo, String autor) {
        public static LivroSimplificadoDTO fromEntity(Livro livro) {
            return new LivroSimplificadoDTO(livro.getId(), livro.getTitulo(), livro.getAutor());
        }
    }
}