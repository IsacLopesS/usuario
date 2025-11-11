package com.isac.business;

import com.isac.business.dto.ViaCepDTO;
import com.isac.infrastructure.clients.ViaCepClient;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ViaCepService {
    private final ViaCepClient client;

    public ViaCepDTO buscarDadosEndereco(String cep){

        String cepFormatado = processarCep(cep);
        return client.buscaDadosEndereco(cepFormatado);
    }

    private String processarCep(String cep){
        String cepFormatado = cep.replace(" ", "")
                .replace("-","");

        if(!cepFormatado.matches("\\d+") || cepFormatado.length() != 8){
            throw new IllegalArgumentException("O cep contem caracteres inválidos, favor verificar");
        }
        return cepFormatado;
    }
}
