package com.br.emakers.apiProjeto.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
// import org.springframework.security.core.userdetails.User; // REMOVIDO: Não é mais necessário
// import org.springframework.security.core.userdetails.UserDetails; // REMOVIDO: Não é mais necessário
import org.springframework.security.core.userdetails.UserDetailsService; // Mantido: É injetado, mas não definido aqui
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.security.provisioning.InMemoryUserDetailsManager; // REMOVIDO: Não é mais necessário
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.http.HttpMethod; // <-- IMPORT NECESSÁRIO PARA HttpMethod

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // O bean userDetailsService foi removido/comentado conforme o passo anterior,
    // e o Spring Security agora usará o PessoaUserDetailsService que você criou.

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Desabilita CSRF (comum para APIs REST sem sessão)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // APIs REST geralmente são stateless
            .authorizeHttpRequests(authorize -> authorize
                // Permitir POST para /pessoas (cadastro) sem autenticação
                .requestMatchers(HttpMethod.POST, "/pessoas").permitAll() // <-- NOVA LINHA

                // Todas as outras requisições exigirão autenticação
                .anyRequest().authenticated()
            )
            .httpBasic(); // <-- AGORA ESTÁ CORRETAMENTE ENCADEADO (removido o ';' acima)

        return http.build();
    }
}