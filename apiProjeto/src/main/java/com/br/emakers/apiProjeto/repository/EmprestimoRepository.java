package com.br.emakers.apiProjeto.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.emakers.apiProjeto.data.Emprestimo;
import com.br.emakers.apiProjeto.data.EmprestimoId; // Importe a classe da chave composta

public interface EmprestimoRepository extends JpaRepository<Emprestimo, EmprestimoId> {
    // Você pode adicionar métodos de consulta personalizados aqui, se necessário.
    // Exemplo: List<Emprestimo> findByIdPessoa(Long idPessoa);
    // Exemplo: List<Emprestimo> findByLivroIdLivro(Long idLivro); // Para buscar por idLivro do livro na relação
    // Ou para verificar se um empréstimo específico existe e não foi devolvido
    // Optional<Emprestimo> findByIdLivroAndIdPessoaAndDataDevolucaoRealIsNull(Long idLivro, Long idPessoa);
}