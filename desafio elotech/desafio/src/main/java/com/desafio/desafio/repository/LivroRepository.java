package com.desafio.desafio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import com.desafio.desafio.model.Livro;


@Repository
public interface LivroRepository extends JpaRepository<Livro, Long> {
    Optional<Livro> findByIsbn(String isbn);

    List<Livro> findByCategoriaAndIdNotIn(String categoria, List<Long> idsLivrosJaEmprestados);
}
