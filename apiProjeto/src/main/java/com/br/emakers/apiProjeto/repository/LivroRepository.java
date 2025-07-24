package com.br.emakers.apiProjeto.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.emakers.apiProjeto.data.Livro; // Importe a classe Livro

public interface LivroRepository extends JpaRepository<Livro, Long> {
    // Você pode adicionar métodos de consulta personalizados aqui, se precisar.
    // Exemplo: Optional<Livro> findByNome(String nome);
}