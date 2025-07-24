package com.br.emakers.apiProjeto.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.br.emakers.apiProjeto.data.Livro; // Importe a classe Livro
import com.br.emakers.apiProjeto.repository.LivroRepository; // Importe o LivroRepository

@Service
public class LivroService {

    private final LivroRepository livroRepository;

    // Injeção de dependência do LivroRepository via construtor
    public LivroService(LivroRepository livroRepository) {
        this.livroRepository = livroRepository;
    }

    public List<Livro> listarTodos() {
        return livroRepository.findAll();
    }

    public Optional<Livro> buscarPorId(Long id) {
        return livroRepository.findById(id);
    }

    public Livro salvar(Livro livro) {
        return livroRepository.save(livro);
    }

    public void deletar(Long id) {
        livroRepository.deleteById(id);
    }
}