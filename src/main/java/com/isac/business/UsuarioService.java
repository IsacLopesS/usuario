package com.isac.business;


import com.isac.business.converter.UsuarioConverter;
import com.isac.business.dto.UsuarioDTO;
import com.isac.infrastructure.entity.Usuario;
import com.isac.infrastructure.exceptions.ConflictException;
import com.isac.infrastructure.exceptions.ResourceNotFoundException;
import com.isac.infrastructure.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;
    private final PasswordEncoder passwordEncoder;

    public UsuarioDTO salvaUsuario(UsuarioDTO usuarioDTO){
        emailExiste(usuarioDTO.getEmail());
        usuarioDTO.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
        Usuario usuario = usuarioConverter.paraUsuario(usuarioDTO);
        return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));
    }

    public void emailExiste(String email){
        try{
            boolean existe = verificaEmailExistente(email);

            if(existe){
                throw new ConflictException("email já cadastrado"+email);
            }
        } catch (ConflictException e) {
            throw new ConflictException("email já cadastrado"+e.getCause());
        }
    }
    public boolean verificaEmailExistente(String email){
        return usuarioRepository.existsByEmail(email);
    }

    public Usuario buscarUsuarioPorEmail(String email){
        return usuarioRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("Email não encontrado"+email));
    }
    public void deletaUsuarioPorEmil(String email){
        usuarioRepository.deleteByEmail(email);
    }


}
