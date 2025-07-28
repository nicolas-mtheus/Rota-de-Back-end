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

import com.br.emakers.apiProjeto.data.Pessoa;
import com.br.emakers.apiProjeto.service.PessoaService;

import jakarta.validation.Valid;

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

    // MÉTODO MODIFICADO: buscarPorId
    @GetMapping("/{id}")
    public ResponseEntity<Pessoa> buscarPorId(@PathVariable Long id) {
        // O service agora retorna a Pessoa diretamente ou lança ResourceNotFoundException
        Pessoa pessoa = pessoaService.buscarPorId(id);
        return ResponseEntity.ok(pessoa);
    }

    @PostMapping
    public ResponseEntity<Pessoa> salvar(@Valid @RequestBody Pessoa pessoa) {
        return ResponseEntity.ok(pessoaService.salvar(pessoa));
    }

    // MÉTODO MODIFICADO: atualizar
    @PutMapping("/{id}")
    public ResponseEntity<Pessoa> atualizar(@PathVariable Long id, @Valid @RequestBody Pessoa pessoa) {
        // O service.buscarPorId agora lança ResourceNotFoundException se não encontrar
        // Então, se chegar aqui, a pessoa existe.
        Pessoa pessoaExistente = pessoaService.buscarPorId(id); // Garante que a pessoa existe

        // Atualiza os campos da pessoa existente com os dados recebidos
        pessoaExistente.setNome(pessoa.getNome());
        pessoaExistente.setCpf(pessoa.getCpf());
        pessoaExistente.setCep(pessoa.getCep());
        pessoaExistente.setEmail(pessoa.getEmail());

        // Atualiza a senha APENAS se uma nova senha for fornecida no request
        if (pessoa.getSenha() != null && !pessoa.getSenha().isEmpty()) {
            pessoaExistente.setSenha(pessoa.getSenha());
        }

        return ResponseEntity.ok(pessoaService.salvar(pessoaExistente));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        // O service.deletar agora lança ResourceNotFoundException se não encontrar
        // Não precisamos mais do Optional.
        pessoaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}