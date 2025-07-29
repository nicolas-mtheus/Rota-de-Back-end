package com.br.emakers.apiProjeto.client; // <-- PACOTE 'client', não 'client.response'

import org.springframework.cloud.openfeign.FeignClient; // Importe o DTO de resposta
import org.springframework.web.bind.annotation.GetMapping; // Importe a anotação @FeignClient
import org.springframework.web.bind.annotation.PathVariable; // Importe @GetMapping

import com.br.emakers.apiProjeto.client.response.EnderecoResponse; // Importe @PathVariable

@FeignClient(name = "viacep", url = "https://viacep.com.br/ws") // Define o nome do cliente e a URL base da API
public interface ViaCepClient {

    @GetMapping("/{cep}/json") // Mapeia o endpoint específico do ViaCEP
    EnderecoResponse buscarEnderecoPorCep(@PathVariable("cep") String cep);
}