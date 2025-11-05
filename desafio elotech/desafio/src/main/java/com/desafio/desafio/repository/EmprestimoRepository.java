package com.desafio.desafio.repository;

import com.desafio.desafio.model.Emprestimo;
import com.desafio.desafio.model.Livro;
import com.desafio.desafio.model.StatusEmprestimo;
import com.desafio.desafio.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> {

    Optional<Emprestimo> findByLivroAndStatus(Livro livro, StatusEmprestimo status);

    List<Emprestimo> findByUsuario(Usuario usuario);
}
