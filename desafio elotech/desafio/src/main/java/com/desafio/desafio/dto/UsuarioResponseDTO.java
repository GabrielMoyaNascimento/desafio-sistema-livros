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
