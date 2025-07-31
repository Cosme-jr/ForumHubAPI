package br.com.alura.forumhub.forumhub.usuario; // Ajuste o pacote conforme a sua estrutura

import jakarta.validation.constraints.NotBlank;

// Usamos um Record para ser um DTO imutável e conciso
public record DadosAutenticacao(
        @NotBlank // Garante que o campo não seja nulo ou vazio
        String login,
        @NotBlank // Garante que o campo não seja nulo ou vazio
        String senha
) {
}
