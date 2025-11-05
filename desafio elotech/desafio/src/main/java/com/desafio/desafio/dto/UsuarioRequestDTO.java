package com.desafio.desafio.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import java.time.LocalDate;

public record UsuarioRequestDTO(
        @NotBlank(message = "O nome é obrigatório")
        String nome,

        @NotBlank(message = "O e-mail é obrigatório")
        @Email(message = "Formato de e-mail inválido")
        String email,

        @NotNull(message = "A data de cadastro é obrigatória")
        @PastOrPresent(message = "A data de cadastro não pode ser no futuro")
        LocalDate dataCadastro,

        @NotBlank(message = "O telefone é obrigatório")
        String telefone
) {}
