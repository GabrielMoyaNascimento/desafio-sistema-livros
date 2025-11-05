package com.desafio.desafio.dto;


import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record EmprestimoRequestDTO(
        @NotNull(message = "O ID do usuário é obrigatório")
        Long usuarioId,

        @NotNull(message = "O ID do livro é obrigatório")
        Long livroId,

        @NotNull(message = "A data de devolução prevista é obrigatória")
        @Future(message = "A data de devolução prevista deve ser no futuro")
        LocalDate dataDevolucaoPrevista
) {}
