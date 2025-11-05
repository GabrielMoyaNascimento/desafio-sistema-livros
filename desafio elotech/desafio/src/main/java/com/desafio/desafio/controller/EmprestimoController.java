package com.desafio.desafio.controller;

import com.desafio.desafio.dto.EmprestimoRequestDTO;
import com.desafio.desafio.dto.EmprestimoResponseDTO;
import com.desafio.desafio.model.Emprestimo;
import com.desafio.desafio.service.EmprestimoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/emprestimos")
public class EmprestimoController {

    @Autowired
    private EmprestimoService emprestimoService;

    /**
     * Endpoint para CRIAR um novo empréstimo (associar um livro a um usuário).
     * Responde a: POST /api/emprestimos
     * @param requestDTO O JSON com os IDs (usuarioId, livroId) e data de devolução.
     * @return O empréstimo criado (Status 201 Created).
     */
    @PostMapping
    public ResponseEntity<EmprestimoResponseDTO> createEmprestimo(@Valid @RequestBody EmprestimoRequestDTO requestDTO) {

        Emprestimo novoEmprestimo = emprestimoService.createEmprestimo(
                requestDTO.usuarioId(),
                requestDTO.livroId(),
                requestDTO.dataDevolucaoPrevista()
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(EmprestimoResponseDTO.fromEntity(novoEmprestimo));
    }

    /**
     * Endpoint para ATUALIZAR um empréstimo (Devolver um livro).
     * Responde a: PATCH /api/emprestimos/{id}/devolver
     * Usamos PATCH pois é uma atualização parcial (apenas status e data).
     *
     * @param id O ID do empréstimo a ser atualizado.
     * @return O empréstimo com o status atualizado para DEVOLVIDO (Status 200 OK).
     */
    @PatchMapping("/{id}/devolver")
    public ResponseEntity<EmprestimoResponseDTO> devolverLivro(@PathVariable Long id) {
        Emprestimo emprestimoDevolvido = emprestimoService.devolverLivro(id);

        return ResponseEntity.ok(EmprestimoResponseDTO.fromEntity(emprestimoDevolvido));
    }
}
