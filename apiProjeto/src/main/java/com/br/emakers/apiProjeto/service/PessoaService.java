package com.br.emakers.apiProjeto.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder; // Já estava no seu código, mantido aqui para clareza
import org.springframework.stereotype.Service;

import com.br.emakers.apiProjeto.client.ViaCepClient;
import com.br.emakers.apiProjeto.client.response.EnderecoResponse;
import com.br.emakers.apiProjeto.data.Pessoa;
import com.br.emakers.apiProjeto.exception.ResourceNotFoundException; // Mantido, conforme seu código
import com.br.emakers.apiProjeto.repository.PessoaRepository; // <-- IMPORT NECESSÁRIO PARA VIACEP


@Service
public class PessoaService {

    private final PessoaRepository pessoaRepository;
    private final PasswordEncoder passwordEncoder; // Mantenha esta linha
    private final ViaCepClient viaCepClient; // <-- NOVA INJEÇÃO DA DEPENDÊNCIA VIACEP

    // Construtor: AGORA INJETA TAMBÉM O ViaCepClient
    public PessoaService(PessoaRepository pessoaRepository, PasswordEncoder passwordEncoder, ViaCepClient viaCepClient) {
        this.pessoaRepository = pessoaRepository;
        this.passwordEncoder = passwordEncoder;
        this.viaCepClient = viaCepClient; // <-- INICIALIZAÇÃO DA DEPENDÊNCIA VIACEP
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

        // NOVO: Chamar a API ViaCEP se o CEP estiver presente ao salvar/atualizar
        if (pessoa.getCep() != null && !pessoa.getCep().isEmpty()) {
            EnderecoResponse endereco = viaCepClient.buscarEnderecoPorCep(pessoa.getCep());
            // Se o ViaCEP retornar erro (CEP não encontrado ou inválido), lançamos uma exceção
            if (endereco.isErro()) {
                throw new ResourceNotFoundException("CEP não encontrado ou inválido: " + pessoa.getCep());
            }
            // NOTA: Os detalhes do endereço (logradouro, bairro, etc.) não são salvos
            // na entidade Pessoa, pois ela não possui esses campos.
            // Apenas confirmamos a validade do CEP. O retorno completo será tratado no Controller/PessoaResponse.
        }

        return pessoaRepository.save(pessoa);
    }

    public void deletar(Long id) {
        // Antes de deletar, podemos verificar se a pessoa existe para lançar 404
        if (!pessoaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Pessoa não encontrada com ID para exclusão: " + id);
        }
        pessoaRepository.deleteById(id);
    }

    // NOVO MÉTODO: Para buscar e retornar o endereço completo de um CEP
    // Este método será chamado pelo Controller para incluir os dados do endereço na resposta.
    public EnderecoResponse buscarEnderecoPorCep(String cep) {
        EnderecoResponse endereco = viaCepClient.buscarEnderecoPorCep(cep);
        // Se o ViaCEP retornar erro, lançamos ResourceNotFoundException
        if (endereco.isErro()) {
            throw new ResourceNotFoundException("CEP não encontrado ou inválido: " + cep);
        }
        return endereco;
    }
}