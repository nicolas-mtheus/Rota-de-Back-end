package com.br.emakers.apiProjeto.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.emakers.apiProjeto.client.response.EnderecoResponse;
import com.br.emakers.apiProjeto.controller.response.PessoaResponse;
import com.br.emakers.apiProjeto.data.Pessoa;
import com.br.emakers.apiProjeto.service.PessoaService; // <-- IMPORT NECESSÁRIO

import jakarta.validation.Valid; // <-- IMPORT NECESSÁRIO

@RestController
@RequestMapping("/pessoas")
public class PessoaController {

    private final PessoaService pessoaService;

    public PessoaController(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }

    @GetMapping
    public List<Pessoa> listarTodos() {
        return pessoaService.listarTodos();
    }

    // MÉTODO: buscarPorId (não retorna endereço diretamente, apenas a Pessoa do DB)
    @GetMapping("/{id}")
    public ResponseEntity<Pessoa> buscarPorId(@PathVariable Long id) {
        Pessoa pessoa = pessoaService.buscarPorId(id);
        return ResponseEntity.ok(pessoa);
    }

    // MÉTODO MODIFICADO: salvar (agora retorna PessoaResponse com dados do ViaCEP)
    @PostMapping
    public ResponseEntity<PessoaResponse> salvar(@Valid @RequestBody Pessoa pessoa) {
        // Salva a pessoa (a senha é criptografada e o CEP validado no service)
        Pessoa pessoaSalva = pessoaService.salvar(pessoa);

        EnderecoResponse endereco = null;
        // Se o CEP foi fornecido e validado, busca os detalhes do endereço para a resposta
        if (pessoaSalva.getCep() != null && !pessoaSalva.getCep().isEmpty()) {
            // A chamada buscarEnderecoPorCep no service já lança exceção se o CEP for inválido
            endereco = pessoaService.buscarEnderecoPorCep(pessoaSalva.getCep());
        }

        // Constrói e retorna o PessoaResponse com os dados da pessoa e do endereço
        return ResponseEntity.ok(new PessoaResponse(pessoaSalva, endereco));
    }

    // MÉTODO MODIFICADO: atualizar (agora retorna PessoaResponse com dados do ViaCEP)
    @PutMapping("/{id}")
    public ResponseEntity<PessoaResponse> atualizar(@PathVariable Long id, @Valid @RequestBody Pessoa pessoa) {
        Pessoa pessoaExistente = pessoaService.buscarPorId(id); // Garante que a pessoa existe

        pessoaExistente.setNome(pessoa.getNome());
        pessoaExistente.setCpf(pessoa.getCpf());
        pessoaExistente.setCep(pessoa.getCep());
        pessoaExistente.setEmail(pessoa.getEmail());
        
        // Atualiza a senha APENAS se uma nova senha for fornecida no request
        if (pessoa.getSenha() != null && !pessoa.getSenha().isEmpty()) {
            pessoaExistente.setSenha(pessoa.getSenha());
        }
        
        // Salva a pessoa atualizada (o CEP é validado novamente no service)
        Pessoa pessoaAtualizada = pessoaService.salvar(pessoaExistente);

        EnderecoResponse endereco = null;
        // Se o CEP foi fornecido e validado, busca os detalhes do endereço para a resposta
        if (pessoaAtualizada.getCep() != null && !pessoaAtualizada.getCep().isEmpty()) {
            // A chamada buscarEnderecoPorCep no service já lança exceção se o CEP for inválido
            endereco = pessoaService.buscarEnderecoPorCep(pessoaAtualizada.getCep());
        }

        // Constrói e retorna o PessoaResponse com os dados da pessoa e do endereço
        return ResponseEntity.ok(new PessoaResponse(pessoaAtualizada, endereco));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        pessoaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}