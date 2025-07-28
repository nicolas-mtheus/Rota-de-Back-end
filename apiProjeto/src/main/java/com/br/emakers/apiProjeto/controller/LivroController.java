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

import com.br.emakers.apiProjeto.data.Livro;
import com.br.emakers.apiProjeto.service.LivroService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/livros")
public class LivroController {

    private final LivroService livroService;

    public LivroController(LivroService livroService) {
        this.livroService = livroService;
    }

    @GetMapping
    public List<Livro> listarTodos() {
        return livroService.listarTodos();
    }

    // MÉTODO MODIFICADO: buscarPorId
    @GetMapping("/{id}")
    public ResponseEntity<Livro> buscarPorId(@PathVariable Long id) {
        // O service agora retorna o Livro diretamente ou lança ResourceNotFoundException
        Livro livro = livroService.buscarPorId(id);
        return ResponseEntity.ok(livro);
    }

    @PostMapping
    public ResponseEntity<Livro> salvar(@Valid @RequestBody Livro livro) {
        return ResponseEntity.ok(livroService.salvar(livro));
    }

    // MÉTODO MODIFICADO: atualizar
    @PutMapping("/{id}")
    public ResponseEntity<Livro> atualizar(@PathVariable Long id, @Valid @RequestBody Livro livro) {
        // O service.buscarPorId agora lança ResourceNotFoundException se não encontrar
        // Então, se chegar aqui, o livro existe.
        Livro livroExistente = livroService.buscarPorId(id); // Garante que o livro existe

        // Atualiza os campos do livro existente com os dados recebidos
        livroExistente.setNome(livro.getNome());
        livroExistente.setAutor(livro.getAutor());
        livroExistente.setData_lancamento(livro.getData_lancamento());

        return ResponseEntity.ok(livroService.salvar(livroExistente));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        // O service.deletar agora lança ResourceNotFoundException se não encontrar
        livroService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}