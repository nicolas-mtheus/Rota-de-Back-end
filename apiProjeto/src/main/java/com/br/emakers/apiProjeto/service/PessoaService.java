package com.br.emakers.apiProjeto.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service; // Mantenha este import

import com.br.emakers.apiProjeto.data.Pessoa;
import com.br.emakers.apiProjeto.exception.ResourceNotFoundException;
import com.br.emakers.apiProjeto.repository.PessoaRepository; // <-- Importe a nova exceção


@Service
public class PessoaService {

    private final PessoaRepository pessoaRepository;
    private final PasswordEncoder passwordEncoder; // Mantenha esta linha

    // Construtor: Verifique se o PasswordEncoder está sendo injetado.
    public PessoaService(PessoaRepository pessoaRepository, PasswordEncoder passwordEncoder) {
        this.pessoaRepository = pessoaRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Pessoa> listarTodos() {
        return pessoaRepository.findAll();
    }

    // MÉTODO MODIFICADO: buscarPorId
    public Pessoa buscarPorId(Long id) { // Retorna Pessoa diretamente, lançando exceção se não encontrar
        return pessoaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pessoa não encontrada com ID: " + id));
    }

    public Pessoa salvar(Pessoa pessoa) {
        // Lógica de criptografia de senha (já implementada)
        String senhaCriptografada = passwordEncoder.encode(pessoa.getSenha());
        pessoa.setSenha(senhaCriptografada);

        return pessoaRepository.save(pessoa);
    }

    public void deletar(Long id) {
        // Antes de deletar, podemos verificar se a pessoa existe para lançar 404
        if (!pessoaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Pessoa não encontrada com ID para exclusão: " + id);
        }
        pessoaRepository.deleteById(id);
    }
}