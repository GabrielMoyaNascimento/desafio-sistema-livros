package com.desafio.desafio.service;


import com.desafio.desafio.exception.BusinessException;
import com.desafio.desafio.exception.ResourceNotFoundException;
import com.desafio.desafio.model.Usuario;
import com.desafio.desafio.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

    public Usuario getUsuarioById(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com id: " + id));
    }

    public Usuario createUsuario(Usuario usuario) {
        usuarioRepository.findByEmail(usuario.getEmail()).ifPresent(u -> {
            throw new BusinessException("E-mail já cadastrado: " + usuario.getEmail());
        });
        return usuarioRepository.save(usuario);
    }

    public Usuario updateUsuario(Long id, Usuario usuarioDetails) {
        Usuario usuario = getUsuarioById(id); // Reusa a validação de existência

        if (!usuario.getEmail().equals(usuarioDetails.getEmail())) {
            usuarioRepository.findByEmail(usuarioDetails.getEmail()).ifPresent(u -> {
                throw new BusinessException("E-mail já cadastrado: " + usuarioDetails.getEmail());
            });
        }

        usuario.setNome(usuarioDetails.getNome());
        usuario.setEmail(usuarioDetails.getEmail());
        usuario.setTelefone(usuarioDetails.getTelefone());

        return usuarioRepository.save(usuario);
    }

    public void deleteUsuario(Long id) {
        Usuario usuario = getUsuarioById(id);
        usuarioRepository.delete(usuario);
    }
}
