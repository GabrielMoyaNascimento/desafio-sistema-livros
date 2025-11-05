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

    /**
     * Endpoint para CRIAR um novo usuário.
     * Responde a: POST /api/usuarios
     * @param requestDTO O JSON do usuário a ser criado (validado por @Valid).
     * @return O usuário criado (Status 201 Created).
     */
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

    /**
     * Endpoint para LER (buscar) todos os usuários.
     * Responde a: GET /api/usuarios
     * @return Uma lista de todos os usuários (Status 200 OK).
     */
    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> getAllUsuarios() {
        List<UsuarioResponseDTO> usuarios = usuarioService.getAllUsuarios().stream()
                .map(UsuarioResponseDTO::fromEntity)
                .collect(Collectors.toList());

        return ResponseEntity.ok(usuarios);
    }

    /**
     * Endpoint para LER (buscar) um usuário específico pelo ID.
     * Responde a: GET /api/usuarios/{id}
     * @param id O ID do usuário (vem da URL).
     * @return O usuário encontrado (Status 200 OK).
     * @throws ResourceNotFoundException (Status 404) se não for encontrado.
     */
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> getUsuarioById(@PathVariable Long id) {
        Usuario usuario = usuarioService.getUsuarioById(id);
        return ResponseEntity.ok(UsuarioResponseDTO.fromEntity(usuario));
    }

    /**
     * Endpoint para ATUALIZAR um usuário existente.
     * Responde a: PUT /api/usuarios/{id}
     * @param id O ID do usuário a ser atualizado (vem da URL).
     * @param requestDTO O JSON com os novos dados (validado por @Valid).
     * @return O usuário atualizado (Status 200 OK).
     */
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

    /**
     * Endpoint para DELETAR um usuário.
     * Responde a: DELETE /api/usuarios/{id}
     * @param id O ID do usuário a ser deletado (vem da URL).
     * @return Nada (Status 204 No Content).
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Long id) {
        usuarioService.deleteUsuario(id);
        return ResponseEntity.noContent().build();
    }
}
