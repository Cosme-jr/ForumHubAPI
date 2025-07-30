// Caminho: br/com/alura/forumhub/forumhub/controller/TopicoController.java
package br.com.alura.forumhub.forumhub.controller;

import br.com.alura.forumhub.forumhub.dto.DadosCadastroTopico;
import br.com.alura.forumhub.forumhub.dto.DadosDetalhamentoTopico;
import br.com.alura.forumhub.forumhub.dto.DadosAtualizacaoTopico;
import br.com.alura.forumhub.forumhub.repositorio.TopicoRepository;
import br.com.alura.forumhub.forumhub.topico.Topico;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page; // NOVO IMPORT
import org.springframework.data.domain.Pageable; // NOVO IMPORT
import org.springframework.data.web.PageableDefault; // NOVO IMPORT
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoRepository topicoRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<String> cadastrar(@RequestBody @Valid DadosCadastroTopico dados, UriComponentsBuilder uriBuilder) {
        if (topicoRepository.existsByTituloAndMensagem(dados.titulo(), dados.mensagem())) {
            return ResponseEntity.badRequest().body("Tópico já existe com o mesmo título e mensagem.");
        }
        var topico = new Topico(dados.titulo(), dados.mensagem(), dados.autor(), dados.curso());
        topicoRepository.save(topico);
        URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(uri).body("Tópico criado com sucesso! ID: " + topico.getId());
    }

    // MÉTODO LISTAR ATUALIZADO
    @GetMapping
    public ResponseEntity<Page<DadosDetalhamentoTopico>> listar(
            @PageableDefault(size = 10, sort = {"dataCriacao"}, direction = org.springframework.data.domain.Sort.Direction.ASC) Pageable paginacao,
            @RequestParam(required = false) String curso,
            @RequestParam(required = false) Integer ano) {

        Page<Topico> page;

        if (curso != null && ano != null) {
            // Se curso e ano forem fornecidos, busca por ambos
            page = topicoRepository.findByCursoAndDataCriacaoYear(curso, ano, paginacao);
        } else if (curso != null) {
            // Se apenas o curso for fornecido, busca por curso
            page = topicoRepository.findByCurso(curso, paginacao);
        } else if (ano != null) {
            // Se apenas o ano for fornecido, busca por ano (precisamos de um método no repo para isso)
            page = topicoRepository.findByDataCriacaoYear(ano, paginacao);
        } else {
            // Se nenhum filtro for fornecido, lista todos com paginação e ordenação padrão
            page = topicoRepository.findAll(paginacao);
        }

        // Mapeia a Page de Topico para uma Page de DadosDetalhamentoTopico
        return ResponseEntity.ok(page.map(DadosDetalhamentoTopico::new));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoTopico> detalhar(@PathVariable Long id) {
        Optional<Topico> topicoOptional = topicoRepository.findById(id);
        if (topicoOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Topico topico = topicoOptional.get();
        return ResponseEntity.ok(new DadosDetalhamentoTopico(topico));
    }

    @PutMapping
    @Transactional
    public ResponseEntity<DadosDetalhamentoTopico> atualizar(@RequestBody @Valid DadosAtualizacaoTopico dados) {
        Optional<Topico> topicoOptional = topicoRepository.findById(dados.id());

        if (topicoOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Topico topico = topicoOptional.get();

        if (dados.titulo() != null) {
            topico.setTitulo(dados.titulo());
        }
        if (dados.mensagem() != null) {
            topico.setMensagem(dados.mensagem());
        }
        if (dados.status() != null) {
            topico.setStatus(dados.status());
        }

        return ResponseEntity.ok(new DadosDetalhamentoTopico(topico));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        if (!topicoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        topicoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}