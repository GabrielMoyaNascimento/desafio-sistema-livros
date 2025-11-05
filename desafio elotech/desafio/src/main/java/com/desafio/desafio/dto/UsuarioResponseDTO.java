package com.desafio.desafio.dto;


import com.desafio.desafio.model.Usuario;

import java.time.LocalDate;


public record UsuarioResponseDTO(
        Long id,
        String nome,
        String email,
        LocalDate dataCadastro,
        String telefone
) {
    /**
     * Método de fábrica para converter a entidade JPA (Usuario)
     * neste DTO (UsuarioResponseDTO).
     *
     * @param usuario A entidade vinda do banco de dados.
     * @return O DTO preenchido para resposta.
     */
    public static UsuarioResponseDTO fromEntity(Usuario usuario) {
        return new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getDataCadastro(),
                usuario.getTelefone()
        );
    }
}
