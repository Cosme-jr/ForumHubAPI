package br.com.alura.forumhub.forumhub.config; // Ajuste o pacote conforme a sua estrutura

import br.com.alura.forumhub.forumhub.config.security.SecurityFilter; // Importa o seu SecurityFilter
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter; // Importa para adicionar o filtro

@Configuration // Indica que esta é uma classe de configuração
@EnableWebSecurity // Habilita o módulo de segurança do Spring Web
public class SecurityConfigurations {

    @Autowired
    private SecurityFilter securityFilter; // Injeta o SecurityFilter que você criou

    // Bean que configura a cadeia de filtros de segurança HTTP
    // Define as políticas de autorização para diferentes requisições
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable()) // Desabilita proteção CSRF para APIs stateless (como REST)
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Define política de sessão como stateless
                .authorizeHttpRequests(req -> {
                    req.requestMatchers("/login").permitAll(); // Permite acesso público ao endpoint de login
                    req.requestMatchers("/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**").permitAll(); // Permite acesso ao Swagger UI
                    req.anyRequest().authenticated(); // Todas as outras requisições exigem autenticação
                })
                // Adiciona o SecurityFilter ANTES do filtro padrão de autenticação por usuário/senha.
                // Isso garante que seu filtro JWT seja executado primeiro para validar o token.
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    // Bean para o gerenciador de autenticação
    // Utilizado para realizar o processo de autenticação
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    // Bean para o codificador de senhas
    // Define o algoritmo de hash para criptografar as senhas
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Recomenda-se BCrypt para hashing de senhas
    }
}