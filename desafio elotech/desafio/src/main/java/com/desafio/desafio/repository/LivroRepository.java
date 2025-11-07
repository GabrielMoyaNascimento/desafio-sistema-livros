package com.desafio.desafio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.desafio.desafio.model.Livro;


@Repository
public interface LivroRepository extends JpaRepository<Livro, Long> {
    Optional<Livro> findByIsbn(String isbn);

    List<Livro> findByCategoriaAndIdNotIn(String categoria, List<Long> idsLivrosJaEmprestados);

    List<Livro> findByCategoria(String categoria);

    /**
     * Busca livros de uma categoria que o utilizador NÃO leu,
     * já trazendo os empréstimos de cada livro.
     */
    @Query("SELECT DISTINCT l FROM Livro l LEFT JOIN FETCH l.emprestimos " +
            "WHERE l.categoria = :categoria AND l.id NOT IN :idsLivrosJaEmprestados")
    List<Livro> findByCategoriaAndIdNotInWithEmprestimos(String categoria, List<Long> idsLivrosJaEmprestados);

    /**
     * Busca TODOS os livros de uma categoria (para usuários novos),
     * já trazendo os empréstimos de cada livro.
     */
    @Query("SELECT DISTINCT l FROM Livro l LEFT JOIN FETCH l.emprestimos " +
            "WHERE l.categoria = :categoria")
    List<Livro> findByCategoriaWithEmprestimos(String categoria);

    @Query("SELECT DISTINCT l.categoria FROM Livro l")
    Set<String> findAllCategorias();
}
