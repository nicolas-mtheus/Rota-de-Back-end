package com.br.emakers.apiProjeto.service;

import com.br.emakers.apiProjeto.data.Pessoa;
import com.br.emakers.apiProjeto.repository.PessoaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections; // Para Collections.emptyList()

@Service
public class PessoaUserDetailsService implements UserDetailsService {

    private final PessoaRepository pessoaRepository;

    public PessoaUserDetailsService(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Busca a Pessoa no banco de dados pelo email
        Pessoa pessoa = pessoaRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Pessoa não encontrada com email: " + email));

        // Retorna um objeto UserDetails que o Spring Security entende.
        // Para simplicidade, vamos usar o construtor User do Spring Security.
        // A senha já está armazenada no objeto Pessoa.
        // As autoridades (roles) são Collections.emptyList() por enquanto, pois não definimos roles.
        return new org.springframework.security.core.userdetails.User(
                pessoa.getEmail(), // O username para o Spring Security é o email da Pessoa
                pessoa.getSenha(), // A senha (já criptografada no banco de dados)
                Collections.emptyList() // Nenhuma autoridade/role por enquanto
        );
    }
}