package br.com.alura.forumhub.forumhub.dto;

import jakarta.validation.constraints.NotBlank;

public record DadosCadastroTopico(
        @NotBlank(message = "Título é obrigatório") // Garante que o campo não seja nulo e não contenha apenas espaços em branco
        String titulo,
        @NotBlank(message = "Mensagem é obrigatória")
        String mensagem,
        @NotBlank(message = "Autor é obrigatório")
        String autor,
        @NotBlank(message = "Curso é obrigatório")
        String curso
) {}
