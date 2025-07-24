package com.br.emakers.apiProjeto.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.br.emakers.apiProjeto.data.Emprestimo;
import com.br.emakers.apiProjeto.data.EmprestimoId;
import com.br.emakers.apiProjeto.data.Livro;
import com.br.emakers.apiProjeto.data.Pessoa; // Importe para controle de transação
import com.br.emakers.apiProjeto.repository.EmprestimoRepository;
import com.br.emakers.apiProjeto.repository.LivroRepository;
import com.br.emakers.apiProjeto.repository.PessoaRepository;

@Service
public class EmprestimoService {

    private final EmprestimoRepository emprestimoRepository;
    private final LivroRepository livroRepository; // Para buscar o livro
    private final PessoaRepository pessoaRepository; // Para buscar a pessoa

    public EmprestimoService(EmprestimoRepository emprestimoRepository,
                             LivroRepository livroRepository,
                             PessoaRepository pessoaRepository) {
        this.emprestimoRepository = emprestimoRepository;
        this.livroRepository = livroRepository;
        this.pessoaRepository = pessoaRepository;
    }

    // Método para listar todos os empréstimos
    public List<Emprestimo> listarTodos() {
        return emprestimoRepository.findAll();
    }

    // Método para buscar um empréstimo por sua chave composta (ID do livro e ID da pessoa)
    public Optional<Emprestimo> buscarPorId(Long idLivro, Long idPessoa) {
        EmprestimoId id = new EmprestimoId(idLivro, idPessoa);
        return emprestimoRepository.findById(id);
    }

    @Transactional // Garante que a operação seja atômica
    public Emprestimo realizarEmprestimo(Long idLivro, Long idPessoa, LocalDate dataDevolucaoPrevista) {
        // 1. Verificar se o livro e a pessoa existem
        Optional<Livro> livroOptional = livroRepository.findById(idLivro);
        Optional<Pessoa> pessoaOptional = pessoaRepository.findById(idPessoa);

        if (livroOptional.isEmpty()) {
            throw new RuntimeException("Livro não encontrado com ID: " + idLivro);
        }
        if (pessoaOptional.isEmpty()) {
            throw new RuntimeException("Pessoa não encontrada com ID: " + idPessoa);
        }

        Livro livro = livroOptional.get();
        Pessoa pessoa = pessoaOptional.get();

        // 2. Verificar se o livro já está emprestado (se dataDevolucaoReal for nula)
        // Isso requer um método personalizado no EmprestimoRepository
        // Por enquanto, vamos assumir que um livro pode ser emprestado várias vezes se não houver um controle de estoque.
        // Para um controle mais robusto, precisaríamos de um campo 'disponivel' no Livro ou uma query mais complexa aqui.
        // Por simplicidade inicial, vamos permitir o empréstimo se ele não existir com dataDevolucaoReal nula
        Optional<Emprestimo> emprestimoExistente = emprestimoRepository.findById(new EmprestimoId(idLivro, idPessoa));
        if (emprestimoExistente.isPresent() && emprestimoExistente.get().getDataDevolucaoReal() == null) {
            // Se o empréstimo já existe e não foi devolvido, não podemos emprestar de novo para a mesma pessoa
            throw new RuntimeException("Livro já emprestado para esta pessoa e ainda não devolvido.");
        }


        // 3. Criar o novo empréstimo
        Emprestimo emprestimo = new Emprestimo(livro, pessoa, LocalDate.now(), dataDevolucaoPrevista);

        // 4. Salvar o empréstimo
        return emprestimoRepository.save(emprestimo);
    }

    @Transactional
    public Emprestimo realizarDevolucao(Long idLivro, Long idPessoa) {
        EmprestimoId id = new EmprestimoId(idLivro, idPessoa);
        Optional<Emprestimo> emprestimoOptional = emprestimoRepository.findById(id);

        if (emprestimoOptional.isEmpty()) {
            throw new RuntimeException("Empréstimo não encontrado para o Livro ID: " + idLivro + " e Pessoa ID: " + idPessoa);
        }

        Emprestimo emprestimo = emprestimoOptional.get();
        if (emprestimo.getDataDevolucaoReal() != null) {
            throw new RuntimeException("Livro já foi devolvido anteriormente.");
        }

        emprestimo.setDataDevolucaoReal(LocalDate.now()); // Define a data real de devolução
        return emprestimoRepository.save(emprestimo);
    }

    // Método para deletar um empréstimo (usado mais para limpeza ou correção, não para devolução normal)
    public void deletarEmprestimo(Long idLivro, Long idPessoa) {
        EmprestimoId id = new EmprestimoId(idLivro, idPessoa);
        emprestimoRepository.deleteById(id);
    }
}