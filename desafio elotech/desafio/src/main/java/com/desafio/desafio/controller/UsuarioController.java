package com.desafio.desafio.controller;


import com.desafio.desafio.dto.UsuarioRequestDTO;
import com.desafio.desafio.dto.UsuarioResponseDTO;
import com.desafio.desafio.exception.ResourceNotFoundException;
import com.desafio.desafio.model.Usuario;
import com.desafio.desafio.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> createUsuario(@Valid @RequestBody UsuarioRequestDTO requestDTO) {
        Usuario usuario = new Usuario();
        usuario.setNome(requestDTO.nome());
        usuario.setEmail(requestDTO.email());
        usuario.setDataCadastro(requestDTO.dataCadastro());
        usuario.setTelefone(requestDTO.telefone());

        Usuario novoUsuario = usuarioService.createUsuario(usuario);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(UsuarioResponseDTO.fromEntity(novoUsuario));
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> getAllUsuarios() {
        List<UsuarioResponseDTO> usuarios = usuarioService.getAllUsuarios().stream()
                .map(UsuarioResponseDTO::fromEntity)
                .collect(Collectors.toList());

        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> getUsuarioById(@PathVariable Long id) {
        Usuario usuario = usuarioService.getUsuarioById(id);
        return ResponseEntity.ok(UsuarioResponseDTO.fromEntity(usuario));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> updateUsuario(@PathVariable Long id, @Valid @RequestBody UsuarioRequestDTO requestDTO) {
        // 1. Converter DTO para Entidade
        Usuario usuarioDetails = new Usuario();
        usuarioDetails.setNome(requestDTO.nome());
        usuarioDetails.setEmail(requestDTO.email());
        usuarioDetails.setTelefone(requestDTO.telefone());

        Usuario usuarioAtualizado = usuarioService.updateUsuario(id, usuarioDetails);

        return ResponseEntity.ok(UsuarioResponseDTO.fromEntity(usuarioAtualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Long id) {
        usuarioService.deleteUsuario(id);
        return ResponseEntity.noContent().build();
    }
}
