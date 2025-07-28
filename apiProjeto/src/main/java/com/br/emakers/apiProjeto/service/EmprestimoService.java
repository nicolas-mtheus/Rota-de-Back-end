package com.br.emakers.apiProjeto.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.br.emakers.apiProjeto.data.Emprestimo;
import com.br.emakers.apiProjeto.data.Livro;
import com.br.emakers.apiProjeto.data.Pessoa;
import com.br.emakers.apiProjeto.exception.BusinessRuleException;
import com.br.emakers.apiProjeto.exception.ResourceNotFoundException;
import com.br.emakers.apiProjeto.repository.EmprestimoRepository;

@Service
public class EmprestimoService {

    private final EmprestimoRepository emprestimoRepository;
    private final LivroService livroService;
    private final PessoaService pessoaService;

    public EmprestimoService(EmprestimoRepository emprestimoRepository,
                             LivroService livroService,
                             PessoaService pessoaService) {
        this.emprestimoRepository = emprestimoRepository;
        this.livroService = livroService;
        this.pessoaService = pessoaService;
    }

    public List<Emprestimo> listarTodos() {
        return emprestimoRepository.findAll();
    }

    // ✅ Atualizado com nome correto do método
    public Optional<Emprestimo> buscarPorId(Long idLivro, Long idPessoa) {
        return emprestimoRepository.findById_IdLivroAndId_IdPessoa(idLivro, idPessoa);
    }

    @Transactional
    public Emprestimo realizarEmprestimo(Long idLivro, Long idPessoa, LocalDate dataDevolucaoPrevista) {
        Livro livro = livroService.buscarPorId(idLivro);
        Pessoa pessoa = pessoaService.buscarPorId(idPessoa);

        Optional<Emprestimo> emprestimoExistente = emprestimoRepository.findEmprestimoAtivoByLivroId(livro.getIdLivro());
        if (emprestimoExistente.isPresent()) {
            throw new BusinessRuleException("Livro com ID " + livro.getIdLivro() + " já está emprestado e não foi devolvido.");
        }

        if (dataDevolucaoPrevista.isBefore(LocalDate.now())) {
            throw new BusinessRuleException("A data de devolução prevista (" + dataDevolucaoPrevista + ") não pode ser no passado.");
        }

        Emprestimo emprestimo = new Emprestimo(livro, pessoa, LocalDate.now(), dataDevolucaoPrevista);
        return emprestimoRepository.save(emprestimo);
    }

    @Transactional
    public Emprestimo realizarDevolucao(Long idLivro, Long idPessoa) {
        Emprestimo emprestimo = emprestimoRepository.findById_IdLivroAndId_IdPessoa(idLivro, idPessoa)
                .orElseThrow(() -> new ResourceNotFoundException("Empréstimo não encontrado para Livro ID: " + idLivro + " e Pessoa ID: " + idPessoa));

        if (emprestimo.getDataDevolucaoReal() != null) {
            throw new BusinessRuleException("Este livro já foi devolvido anteriormente.");
        }

        emprestimo.setDataDevolucaoReal(LocalDate.now());
        return emprestimoRepository.save(emprestimo);
    }

    @Transactional
    public void deletarEmprestimo(Long idLivro, Long idPessoa) {
        Emprestimo emprestimo = emprestimoRepository.findById_IdLivroAndId_IdPessoa(idLivro, idPessoa)
                .orElseThrow(() -> new ResourceNotFoundException("Empréstimo não encontrado para Livro ID: " + idLivro + " e Pessoa ID: " + idPessoa + " para exclusão."));

        emprestimoRepository.delete(emprestimo);
    }
}
