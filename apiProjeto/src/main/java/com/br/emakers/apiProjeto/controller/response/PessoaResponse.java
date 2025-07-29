package com.br.emakers.apiProjeto.controller.response;

import com.br.emakers.apiProjeto.client.response.EnderecoResponse; // Importe o DTO do ViaCEP
import com.br.emakers.apiProjeto.data.Pessoa; // Importe a entidade Pessoa
import com.fasterxml.jackson.annotation.JsonInclude; // Para incluir campos apenas se não forem nulos

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL) // Inclui campos no JSON apenas se não forem nulos
public class PessoaResponse {

    private Long idPessoa;
    private String nome;
    private String cpf;
    private String cep;
    private String email;
    // A senha não é incluída na resposta devido ao @JsonIgnore na entidade Pessoa

    private EnderecoResponse endereco; // Para incluir os detalhes do endereço na resposta

    // Construtor que converte uma Pessoa e, opcionalmente, adiciona o endereço
    public PessoaResponse(Pessoa pessoa, EnderecoResponse endereco) {
        this.idPessoa = pessoa.getIdPessoa();
        this.nome = pessoa.getNome();
        this.cpf = pessoa.getCpf();
        this.cep = pessoa.getCep();
        this.email = pessoa.getEmail();
        this.endereco = endereco; // Pode ser nulo se não houver endereço ou se não foi buscado
    }

    // Construtor sem endereço, caso seja necessário (ex: listar todas as pessoas)
    public PessoaResponse(Pessoa pessoa) {
        this.idPessoa = pessoa.getIdPessoa();
        this.nome = pessoa.getNome();
        this.cpf = pessoa.getCpf();
        this.cep = pessoa.getCep();
        this.email = pessoa.getEmail();
        // Endereco será null e, devido ao @JsonInclude, não aparecerá no JSON
    }
}