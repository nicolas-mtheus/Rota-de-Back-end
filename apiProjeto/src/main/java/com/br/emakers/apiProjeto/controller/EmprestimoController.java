package com.br.emakers.apiProjeto.controller;

import java.util.List; // Importe a DTO

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping; // Para status HTTP específicos
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.emakers.apiProjeto.controller.request.EmprestimoRequest;
import com.br.emakers.apiProjeto.data.Emprestimo;
import com.br.emakers.apiProjeto.service.EmprestimoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/emprestimos") // Mapeamento para /emprestimos
public class EmprestimoController {

    private final EmprestimoService emprestimoService;

    public EmprestimoController(EmprestimoService emprestimoService) {
        this.emprestimoService = emprestimoService;
    }

    // Endpoint para listar todos os empréstimos
    @GetMapping
    public List<Emprestimo> listarTodos() {
        return emprestimoService.listarTodos();
    }

    // Endpoint para buscar um empréstimo específico por ID do Livro e ID da Pessoa
    @GetMapping("/{idLivro}/{idPessoa}")
    public ResponseEntity<Emprestimo> buscarPorId(@PathVariable Long idLivro, @PathVariable Long idPessoa) {
        return emprestimoService.buscarPorId(idLivro, idPessoa)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Endpoint para realizar um novo empréstimo
    @PostMapping
    public ResponseEntity<?> realizarEmprestimo(@Valid @RequestBody EmprestimoRequest request) {
        try {
            Emprestimo novoEmprestimo = emprestimoService.realizarEmprestimo(
                    request.getIdLivro(),
                    request.getIdPessoa(),
                    request.getDataDevolucaoPrevista()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(novoEmprestimo); // Retorna 201 Created
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage()); // Ex: Livro/Pessoa não encontrados, livro já emprestado
        }
    }

    // Endpoint para realizar a devolução de um livro
    @PutMapping("/{idLivro}/{idPessoa}/devolver") // PUT para devolução
    public ResponseEntity<?> realizarDevolucao(@PathVariable Long idLivro, @PathVariable Long idPessoa) {
        try {
            Emprestimo emprestimoDevolvido = emprestimoService.realizarDevolucao(idLivro, idPessoa);
            return ResponseEntity.ok(emprestimoDevolvido);
        } catch (RuntimeException e) {
            // Pode ser 404 Not Found se o empréstimo não existir
            // Ou 400 Bad Request se o livro já foi devolvido
            if (e.getMessage().contains("não encontrado")) {
                 return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            } else {
                 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            }
        }
    }

    // Endpoint para deletar um registro de empréstimo (cuidado ao usar, é mais para limpeza)
    @DeleteMapping("/{idLivro}/{idPessoa}")
    public ResponseEntity<Void> deletarEmprestimo(@PathVariable Long idLivro, @PathVariable Long idPessoa) {
        try {
            emprestimoService.deletarEmprestimo(idLivro, idPessoa);
            return ResponseEntity.noContent().build(); // 204 No Content
        } catch (Exception e) {
            // Idealmente, um tratamento de exceção mais específico
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}