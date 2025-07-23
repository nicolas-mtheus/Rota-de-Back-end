package com.br.emakers.apiProjeto.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.br.emakers.apiProjeto.data.Pessoa;
import com.br.emakers.apiProjeto.repository.PessoaRepository;


@Service
public class PessoaService {
    
     private final PessoaRepository pessoaRepository;

    public PessoaService(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    public List<Pessoa> listarTodos() {
        return pessoaRepository.findAll();
    }

    public Optional<Pessoa> buscarPorId(Long id) {
        return pessoaRepository.findById(id);
    }

    public Pessoa salvar(Pessoa pessoa) {
        return pessoaRepository.save(pessoa);
    }

    public void deletar(Long id) {
        pessoaRepository.deleteById(id);
    }
}
