package com.desafio.desafio.controller;

import com.desafio.desafio.dto.LivroRequestDTO;
import com.desafio.desafio.dto.LivroResponseDTO;
import com.desafio.desafio.model.Livro;
import com.desafio.desafio.service.LivroService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/livros")
public class LivroController {

    @Autowired
    private LivroService livroService;

    @GetMapping
    public ResponseEntity<List<LivroResponseDTO>> getAllLivros() {
        List<LivroResponseDTO> livros = livroService.getAllLivros().stream()
                .map(LivroResponseDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(livros);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LivroResponseDTO> getLivroById(@PathVariable Long id) {
        Livro livro = livroService.getLivroById(id);
        return ResponseEntity.ok(LivroResponseDTO.fromEntity(livro));
    }

    @PostMapping
    public ResponseEntity<LivroResponseDTO> createLivro(@Valid @RequestBody LivroRequestDTO requestDTO) {
        Livro livro = new Livro();
        livro.setTitulo(requestDTO.titulo());
        livro.setAutor(requestDTO.autor());
        livro.setIsbn(requestDTO.isbn());
        livro.setDataPublicacao(requestDTO.dataPublicacao());
        livro.setCategoria(requestDTO.categoria());

        Livro novoLivro = livroService.createLivro(livro);
        return ResponseEntity.status(HttpStatus.CREATED).body(LivroResponseDTO.fromEntity(novoLivro));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LivroResponseDTO> updateLivro(@PathVariable Long id, @Valid @RequestBody LivroRequestDTO requestDTO) {
        Livro livroDetails = new Livro();
        livroDetails.setTitulo(requestDTO.titulo());
        livroDetails.setAutor(requestDTO.autor());
        livroDetails.setIsbn(requestDTO.isbn());
        livroDetails.setDataPublicacao(requestDTO.dataPublicacao());
        livroDetails.setCategoria(requestDTO.categoria());

        Livro livroAtualizado = livroService.updateLivro(id, livroDetails);
        return ResponseEntity.ok(LivroResponseDTO.fromEntity(livroAtualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLivro(@PathVariable Long id) {
        livroService.deleteLivro(id);
        return ResponseEntity.noContent().build();
    }
}