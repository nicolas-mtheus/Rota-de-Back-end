package com.br.emakers.apiProjeto.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.emakers.apiProjeto.data.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
    // Exemplo de consulta personalizada:
    // Optional<Pessoa> findByCpf(String cpf);
}
