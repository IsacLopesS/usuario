package com.isac.business;


import com.isac.business.converter.UsuarioConverter;
import com.isac.business.dto.EnderecoDTO;
import com.isac.business.dto.TelefoneDTO;
import com.isac.business.dto.UsuarioDTO;
import com.isac.infrastructure.entity.Endereco;
import com.isac.infrastructure.entity.Telefone;
import com.isac.infrastructure.entity.Usuario;
import com.isac.infrastructure.exceptions.ConflictException;
import com.isac.infrastructure.exceptions.ResourceNotFoundException;
import com.isac.infrastructure.repository.EnderecoRepository;
import com.isac.infrastructure.repository.TelefoneRepository;
import com.isac.infrastructure.repository.UsuarioRepository;
import com.isac.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final EnderecoRepository enderecoRepository;
    private final TelefoneRepository telefoneRepository;

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

    public UsuarioDTO buscarUsuarioPorEmail(String email){
        try {
            return usuarioConverter.paraUsuarioDTO(usuarioRepository.findByEmail(email).orElseThrow(
                    () -> new ResourceNotFoundException("Email não encontrado" + email)));
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Email não encontrado" + email);
        }
    }
    public void deletaUsuarioPorEmil(String email){
        usuarioRepository.deleteByEmail(email);
    }

    public UsuarioDTO atualizaDadosUsuario(String token, UsuarioDTO usuarioDTO){
       String email = jwtUtil.extrairEmailToken(token.substring(7));

       //criptografa a senha
       usuarioDTO.setSenha(usuarioDTO.getSenha()==null ? null : passwordEncoder.encode(usuarioDTO.getSenha()));

       Usuario usuarioEntity = usuarioRepository.findByEmail(email).orElseThrow(() ->
               new ResourceNotFoundException("Email não localizado"));

       Usuario usuarioAtualizado = usuarioConverter.updateUsuario(usuarioDTO,usuarioEntity);


       return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuarioAtualizado));
    }

    public EnderecoDTO atualizaEndereco(Long idEndereco, EnderecoDTO dto){
        Endereco enderecoENtity = enderecoRepository.findById(idEndereco).orElseThrow(() ->
                new ResourceNotFoundException("id do endereço não localizado: "+idEndereco));

        Endereco enderecoAtualizado = usuarioConverter.updateEndereco(dto, enderecoENtity);
        return usuarioConverter.paraEnderecoDTO(enderecoRepository.save(enderecoAtualizado));

    }

    public TelefoneDTO atualizaTelefone(Long idTelefone,TelefoneDTO dto){
        Telefone entity = telefoneRepository.findById(idTelefone).orElseThrow(() ->
                new ResourceNotFoundException("id do telefone nao encontrado: "+idTelefone));

        Telefone telefoneAtualizado = usuarioConverter.updateTelefone(dto, entity);

        return usuarioConverter.paraTelefoneDTO(telefoneRepository.save(telefoneAtualizado));

    }



}
