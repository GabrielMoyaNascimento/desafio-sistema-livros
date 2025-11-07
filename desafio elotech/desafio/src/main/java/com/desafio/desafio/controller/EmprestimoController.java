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

    @PatchMapping("/{id}/devolver")
    public ResponseEntity<EmprestimoResponseDTO> devolverLivro(@PathVariable Long id) {
        Emprestimo emprestimoDevolvido = emprestimoService.devolverLivro(id);

        return ResponseEntity.ok(EmprestimoResponseDTO.fromEntity(emprestimoDevolvido));
    }
}
