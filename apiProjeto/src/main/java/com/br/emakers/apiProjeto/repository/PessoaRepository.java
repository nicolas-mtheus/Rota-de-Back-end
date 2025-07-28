package com.br.emakers.apiProjeto.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.emakers.apiProjeto.data.Pessoa;
import java.util.Optional; // <-- Adicione esta linha!

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
    // Exemplo de consulta personalizada:
    // Optional<Pessoa> findByCpf(String cpf);

    // Método para buscar uma Pessoa pelo email.
    Optional<Pessoa> findByEmail(String email);
}