package com.br.emakers.apiProjeto.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.br.emakers.apiProjeto.data.Emprestimo;
import com.br.emakers.apiProjeto.data.EmprestimoId;

@Repository
public interface EmprestimoRepository extends JpaRepository<Emprestimo, EmprestimoId> {

    // ✅ Correto: acessa os atributos da chave composta (EmprestimoId)
    Optional<Emprestimo> findById_IdLivroAndId_IdPessoa(Long idLivro, Long idPessoa);

    // Continuação do método de verificação de empréstimo ativo
    @Query("SELECT e FROM Emprestimo e WHERE e.id.idLivro = :idLivro AND e.dataDevolucaoReal IS NULL")
    Optional<Emprestimo> findEmprestimoAtivoByLivroId(Long idLivro);
}
