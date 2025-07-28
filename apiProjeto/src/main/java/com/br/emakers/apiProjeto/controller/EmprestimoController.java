package com.br.emakers.apiProjeto.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping; // Manter este import
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping; // Manter este import se você usar Optional em algum lugar
import org.springframework.web.bind.annotation.RestController;

import com.br.emakers.apiProjeto.controller.request.EmprestimoRequest;
import com.br.emakers.apiProjeto.data.Emprestimo;
import com.br.emakers.apiProjeto.service.EmprestimoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/emprestimos")
public class EmprestimoController {

    private final EmprestimoService emprestimoService;

    public EmprestimoController(EmprestimoService emprestimoService) {
        this.emprestimoService = emprestimoService;
    }

    @GetMapping
    public List<Emprestimo> listarTodos() {
        return emprestimoService.listarTodos();
    }

    // MÉTODO MODIFICADO: buscarPorId
    @GetMapping("/{idLivro}/{idPessoa}")
    public ResponseEntity<Emprestimo> buscarPorId(@PathVariable Long idLivro, @PathVariable Long idPessoa) {
        // O service agora retorna Optional, então fazemos o orElseThrow aqui.
        // A exceção ResourceNotFoundException será capturada pelo GlobalExceptionHandler.
        Emprestimo emprestimo = emprestimoService.buscarPorId(idLivro, idPessoa)
                                            .orElseThrow(() -> new com.br.emakers.apiProjeto.exception.ResourceNotFoundException(
                                                "Empréstimo não encontrado para Livro ID: " + idLivro + " e Pessoa ID: " + idPessoa));
        return ResponseEntity.ok(emprestimo);
    }

    // MÉTODO MODIFICADO: realizarEmprestimo (removido try-catch)
    @PostMapping
    public ResponseEntity<Emprestimo> realizarEmprestimo(@Valid @RequestBody EmprestimoRequest request) {
        Emprestimo novoEmprestimo = emprestimoService.realizarEmprestimo(
                request.getIdLivro(),
                request.getIdPessoa(),
                request.getDataDevolucaoPrevista()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(novoEmprestimo); // Retorna 201 Created
    }

    // MÉTODO MODIFICADO: realizarDevolucao (removido try-catch)
    @PutMapping("/{idLivro}/{idPessoa}/devolver")
    public ResponseEntity<Emprestimo> realizarDevolucao(@PathVariable Long idLivro, @PathVariable Long idPessoa) {
        Emprestimo emprestimoDevolvido = emprestimoService.realizarDevolucao(idLivro, idPessoa);
        return ResponseEntity.ok(emprestimoDevolvido);
    }

    // MÉTODO MODIFICADO: deletarEmprestimo (removido try-catch)
    @DeleteMapping("/{idLivro}/{idPessoa}")
    public ResponseEntity<Void> deletarEmprestimo(@PathVariable Long idLivro, @PathVariable Long idPessoa) {
        emprestimoService.deletarEmprestimo(idLivro, idPessoa);
        return ResponseEntity.noContent().build(); // 204 No Content
    }
}