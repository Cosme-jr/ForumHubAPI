package br.com.alura.forumhub.forumhub.usuario; // Certifique-se que o nome do pacote está correto para sua estrutura

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Table(name = "usuarios") // Mapeia para a tabela 'usuarios' no banco de dados
@Entity(name = "Usuario") // Define o nome da entidade para JPA
@Getter // Gera getters Lombok
@NoArgsConstructor // Construtor sem argumentos Lombok
@AllArgsConstructor // Construtor com todos os argumentos Lombok
@EqualsAndHashCode(of = "id") // Gera equals e hashCode baseado no ID
public class Usuario implements UserDetails { // Implementa UserDetails para Spring Security

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String login; // O nome de usuário para login
    private String senha; // A senha (já será criptografada)
    private String email;
    private Boolean ativo;

    @Column(name = "data_cadastro")
    private LocalDateTime dataCadastro;

    // Construtor para uso no momento de salvar um novo usuário (excluindo id e ativo/dataCadastro inicial)
    public Usuario(String login, String senha, String email) {
        this.login = login;
        this.senha = senha;
        this.email = email;
        this.ativo = true; // Novo usuário é ativo por padrão
        this.dataCadastro = LocalDateTime.now();
    }

    // Métodos da interface UserDetails para Spring Security
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Por enquanto, vamos retornar uma autoridade simples.
        // Em projetos mais complexos, teríamos uma tabela de perfis/roles.
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Conta nunca expira para este exemplo
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Conta nunca é bloqueada para este exemplo
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Credenciais nunca expiram para este exemplo
    }

    @Override
    public boolean isEnabled() {
        return ativo; // Habilitado se o campo 'ativo' for true
    }

    // Métodos para desativar/ativar o usuário (se necessário)
    public void desativar() {
        this.ativo = false;
    }

    public void ativar() {
        this.ativo = true;
    }
}
