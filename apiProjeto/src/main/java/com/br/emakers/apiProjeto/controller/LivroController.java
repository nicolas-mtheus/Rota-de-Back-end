package com.br.emakers.apiProjeto.controller;

import java.util.List; // Importe a classe Livro

import org.springframework.http.ResponseEntity; // Importe o LivroService
import org.springframework.web.bind.annotation.DeleteMapping; // Importe para validação
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.emakers.apiProjeto.data.Livro;
import com.br.emakers.apiProjeto.service.LivroService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/livros") // Mapeamento para /livros
public class LivroController {

    private final LivroService livroService;

    // Injeção de dependência do LivroService via construtor
    public LivroController(LivroService livroService) {
        this.livroService = livroService;
    }

    @GetMapping
    public List<Livro> listarTodos() {
        return livroService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Livro> buscarPorId(@PathVariable Long id) {
        return livroService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Livro> salvar(@Valid @RequestBody Livro livro) {
        // Ao salvar um livro, ele pode já ter um ID (para atualização) ou não (para criação)
        return ResponseEntity.ok(livroService.salvar(livro));
    }

    @PutMapping("/{id}") // Adicionado para atualização (PUT)
    public ResponseEntity<Livro> atualizar(@PathVariable Long id, @Valid @RequestBody Livro livro) {
        return livroService.buscarPorId(id)
                .map(livroExistente -> {
                    // Atualiza os campos do livro existente com os dados do livro recebido
                    livroExistente.setNome(livro.getNome());
                    livroExistente.setAutor(livro.getAutor());
                    livroExistente.setData_lancamento(livro.getData_lancamento());
                    return ResponseEntity.ok(livroService.salvar(livroExistente));
                })
                .orElse(ResponseEntity.notFound().build());
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        livroService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}