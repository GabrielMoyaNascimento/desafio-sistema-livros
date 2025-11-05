package com.desafio.desafio.service;


import com.desafio.desafio.exception.ResourceNotFoundException;
import com.desafio.desafio.model.Emprestimo;
import com.desafio.desafio.model.Livro;
import com.desafio.desafio.model.Usuario;
import com.desafio.desafio.repository.EmprestimoRepository;
import com.desafio.desafio.repository.LivroRepository;
import com.desafio.desafio.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RecomendacaoService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EmprestimoRepository emprestimoRepository;

    @Autowired
    private LivroRepository livroRepository;

    public List<Livro> getRecomendacoes(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado: " + usuarioId));

        List<Emprestimo> emprestimosDoUsuario = emprestimoRepository.findByUsuario(usuario);

        if (emprestimosDoUsuario.isEmpty()) {
            return List.of(); // Sem histórico, sem recomendações
        }

        List<Long> idsLivrosJaEmprestados = emprestimosDoUsuario.stream()
                .map(emprestimo -> emprestimo.getLivro().getId())
                .toList();

        Set<String> categoriasFavoritas = emprestimosDoUsuario.stream()
                .map(emprestimo -> emprestimo.getLivro().getCategoria())
                .collect(Collectors.toSet());

        return categoriasFavoritas.stream()
                .flatMap(categoria ->
                        livroRepository.findByCategoriaAndIdNotIn(categoria, idsLivrosJaEmprestados).stream()
                )
                .distinct()
                .collect(Collectors.toList());
    }
}