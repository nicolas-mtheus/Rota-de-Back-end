package com.br.emakers.apiProjeto.client.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class EnderecoResponse {

    private String cep;
    private String logradouro;
    private String complemento;
    private String bairro;
    private String localidade; // Cidade
    private String uf;         // Estado (ex: SP, MG)
    private String ibge;
    private String gia;
    private String ddd;
    private String siafi;
    
    @JsonProperty("erro")
    private boolean erro;
}