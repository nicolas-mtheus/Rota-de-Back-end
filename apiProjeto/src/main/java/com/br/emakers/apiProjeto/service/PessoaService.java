package com.br.emakers.apiProjeto.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service; // <-- IMPORT NECESSÁRIO

import com.br.emakers.apiProjeto.data.Pessoa;
import com.br.emakers.apiProjeto.repository.PessoaRepository;


@Service
public class PessoaService {

    private final PessoaRepository pessoaRepository;
    private final PasswordEncoder passwordEncoder; // <-- INJEÇÃO DA DEPENDÊNCIA

    // CONSTRUTOR AGORA INJETA TAMBÉM O PasswordEncoder
    public PessoaService(PessoaRepository pessoaRepository, PasswordEncoder passwordEncoder) {
        this.pessoaRepository = pessoaRepository;
        this.passwordEncoder = passwordEncoder; // <-- INICIALIZAÇÃO DA DEPENDÊNCIA
    }

    public List<Pessoa> listarTodos() {
        return pessoaRepository.findAll();
    }

    public Optional<Pessoa> buscarPorId(Long id) {
        return pessoaRepository.findById(id);
    }

    public Pessoa salvar(Pessoa pessoa) {
        // LÓGICA PARA CRIPTOGRAFAR A SENHA ANTES DE SALVAR NO BANCO DE DADOS
        String senhaCriptografada = passwordEncoder.encode(pessoa.getSenha());
        pessoa.setSenha(senhaCriptografada); // Define a senha criptografada na entidade Pessoa

        return pessoaRepository.save(pessoa);
    }

    public void deletar(Long id) {
        pessoaRepository.deleteById(id);
    }
}