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

    @GetMapping("/{id}")
    public ResponseEntity<Pessoa> buscarPorId(@PathVariable Long id) {
        return pessoaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Pessoa> salvar(@Valid @RequestBody Pessoa pessoa) {
        return ResponseEntity.ok(pessoaService.salvar(pessoa));
    }

     // NOVO MÉTODO: ATUALIZAR PESSOA (PUT)
    @PutMapping("/{id}")
    public ResponseEntity<Pessoa> atualizar(@PathVariable Long id, @Valid @RequestBody Pessoa pessoa) {
        return pessoaService.buscarPorId(id)
                .map(pessoaExistente -> {
                    // Atualiza os campos da pessoa existente com os dados recebidos
                    pessoaExistente.setNome(pessoa.getNome());
                    pessoaExistente.setCpf(pessoa.getCpf());
                    pessoaExistente.setCep(pessoa.getCep());
                    pessoaExistente.setEmail(pessoa.getEmail());

                    // Atualiza a senha APENAS se uma nova senha for fornecida no request
                    // (o PessoaService já cuida da criptografia ao chamar salvar)
                    if (pessoa.getSenha() != null && !pessoa.getSenha().isEmpty()) {
                        pessoaExistente.setSenha(pessoa.getSenha());
                    }

                    return ResponseEntity.ok(pessoaService.salvar(pessoaExistente));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        pessoaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}

