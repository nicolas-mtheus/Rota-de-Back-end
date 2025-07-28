package com.br.emakers.apiProjeto.service;

import java.util.List;

import org.springframework.stereotype.Service; // Mantenha este import, pode ser útil

import com.br.emakers.apiProjeto.data.Livro;
import com.br.emakers.apiProjeto.exception.ResourceNotFoundException;
import com.br.emakers.apiProjeto.repository.LivroRepository;


@Service
public class LivroService {

    private final LivroRepository livroRepository;

    public LivroService(LivroRepository livroRepository) {
        this.livroRepository = livroRepository;
    }

    public List<Livro> listarTodos() {
        return livroRepository.findAll();
    }

    // MÉTODO MODIFICADO: buscarPorId
    public Livro buscarPorId(Long id) { // Retorna Livro diretamente, lançando exceção se não encontrar
        return livroRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Livro não encontrado com ID: " + id));
    }

    public Livro salvar(Livro livro) {
        return livroRepository.save(livro);
    }

    // MÉTODO MODIFICADO: deletar
    public void deletar(Long id) {
        // Antes de deletar, podemos verificar se o livro existe para lançar 404
        if (!livroRepository.existsById(id)) {
            throw new ResourceNotFoundException("Livro não encontrado com ID para exclusão: " + id);
        }
        livroRepository.deleteById(id);
    }
}