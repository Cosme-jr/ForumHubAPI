// Caminho: br/com/alura/forumhub/forumhub/dto/DadosAtualizacaoTopico.java
package br.com.alura.forumhub.forumhub.dto;

import br.com.alura.forumhub.forumhub.topico.StatusTopico;
import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoTopico(
        @NotNull(message = "ID do tópico é obrigatório para atualização")
        Long id,
        String titulo,
        String mensagem,
        StatusTopico status
) {}