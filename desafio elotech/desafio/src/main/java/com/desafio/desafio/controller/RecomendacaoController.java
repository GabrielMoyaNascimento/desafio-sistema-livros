package com.desafio.desafio.controller;

import com.desafio.desafio.dto.LivroResponseDTO;
import com.desafio.desafio.service.RecomendacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/recomendacoes")
public class RecomendacaoController {

    @Autowired
    private RecomendacaoService recomendacaoService;

    @GetMapping
    public ResponseEntity<List<LivroResponseDTO>> getRecomendacoes(@RequestParam Long usuarioId) {

        List<LivroResponseDTO> recomendacoes = recomendacaoService.getRecomendacoes(usuarioId)
                .stream()
                .map(LivroResponseDTO::fromEntity)
                .collect(Collectors.toList());

        return ResponseEntity.ok(recomendacoes);
    }
}
