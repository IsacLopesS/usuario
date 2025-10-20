package com.isac.business.converter;

import com.isac.business.dto.EnderecoDTO;
import com.isac.business.dto.TelefoneDTO;
import com.isac.business.dto.UsuarioDTO;
import com.isac.infrastructure.entity.Endereco;
import com.isac.infrastructure.entity.Telefone;
import com.isac.infrastructure.entity.Usuario;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UsuarioConverter {

    /* sem o @builder ficaria assim - vc cria um Usuario a partir de um UsuarioDTO:
    public Usuario paraUsuario(UsuarioDTO usuarioDTO){
        Usuario usuario = new Usuario();

        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setNome(usuarioDTO.getNome());
        ...
        return usuario;
    }*/

    //com o builder fica mais limpo, veja:
    public Usuario paraUsuario(UsuarioDTO usuarioDTO){
        return Usuario.builder()
                .nome(usuarioDTO.getNome())
                .email(usuarioDTO.getEmail())
                .senha(usuarioDTO.getSenha())
                .enderecos(paraListaEndereco(usuarioDTO.getEnderecos()))
                .telefones(paraListTelefone(usuarioDTO.getTelefones()))
                .build();
    }
    public List<Endereco> paraListaEndereco(List<EnderecoDTO> enderecoDTOS){

        // jeito mais avançado e menos verboso:
       // return enderecoDTOS.stream().map(this::paraEndereco).toList();

        //jeito mais facil de enteder:
        List<Endereco> enderecos = new ArrayList<>();

        for(EnderecoDTO enderecoDTO : enderecoDTOS){
            enderecos.add(paraEndereco(enderecoDTO));
        }
        return enderecos;
    }
    public Endereco paraEndereco(EnderecoDTO enderecoDTO){
        return Endereco.builder()
                .cep(enderecoDTO.getCep())
                .rua(enderecoDTO.getRua())
                .cidade(enderecoDTO.getCidade())
                .complemento(enderecoDTO.getComplemento())
                .estado(enderecoDTO.getEstado())
                .numero(enderecoDTO.getNumero())
                .build();
    }
    public List<Telefone> paraListTelefone(List<TelefoneDTO> telefoneDTOs){
        return telefoneDTOs.stream().map(this::paraTelefone).toList();
    }
     public Telefone paraTelefone(TelefoneDTO telefoneDTO){
        return Telefone.builder()
                .ddd(telefoneDTO.getDdd())
                .numero(telefoneDTO.getNumero())
                .build();
     }

     /*-----------------------------------------------------------------------------------------*/
     public UsuarioDTO paraUsuarioDTO(Usuario usuario){
         return UsuarioDTO.builder()
                 .nome(usuario.getNome())
                 .email(usuario.getEmail())
                 .senha(usuario.getSenha())
                 .enderecos(paraListaEnderecoDTO(usuario.getEnderecos()))
                 .telefones(paraListTelefoneDTO(usuario.getTelefones()))
                 .build();
     }
    public List<EnderecoDTO> paraListaEnderecoDTO(List<Endereco> enderecos){

         return enderecos.stream().map(this::paraEnderecoDTO).toList();
    }
    public EnderecoDTO paraEnderecoDTO(Endereco endereco){
        return EnderecoDTO.builder()
                .cep(endereco.getCep())
                .rua(endereco.getRua())
                .cidade(endereco.getCidade())
                .complemento(endereco.getComplemento())
                .estado(endereco.getEstado())
                .numero(endereco.getNumero())
                .build();
    }
    public List<TelefoneDTO> paraListTelefoneDTO(List<Telefone> telefones){
        return telefones.stream().map(this::paraTelefoneDTO).toList();
    }
    public TelefoneDTO paraTelefoneDTO(Telefone telefone){
        return TelefoneDTO.builder()
                .ddd(telefone.getDdd())
                .numero(telefone.getNumero())
                .build();
    }

}
