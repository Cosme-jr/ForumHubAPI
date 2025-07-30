// Caminho: br/com/alura/forumhub/forumhub/repositorio/TopicoRepository.java
package br.com.alura.forumhub.forumhub.repositorio;

import br.com.alura.forumhub.forumhub.topico.Topico;
import org.springframework.data.domain.Page; // NOVO IMPORT
import org.springframework.data.domain.Pageable; // NOVO IMPORT
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query; // NOVO IMPORT
import org.springframework.stereotype.Repository;

@Repository
public interface TopicoRepository extends JpaRepository<Topico, Long> {

    boolean existsByTituloAndMensagem(String titulo, String mensagem);

    // Método para listar todos os tópicos com paginação e ordenação
    Page<Topico> findAll(Pageable paginacao);

    // Novo método para buscar por curso com paginação
    Page<Topico> findByCurso(String curso, Pageable paginacao);

    // Novo método para buscar por ano de criação (extraindo o ano da dataCriacao)
    @Query("SELECT t FROM Topico t WHERE YEAR(t.dataCriacao) = :ano")
    Page<Topico> findByDataCriacaoYear(Integer ano, Pageable paginacao);

    // Novo método para buscar por curso E ano de criação
    @Query("SELECT t FROM Topico t WHERE t.curso = :curso AND YEAR(t.dataCriacao) = :ano")
    Page<Topico> findByCursoAndDataCriacaoYear(String curso, Integer ano, Pageable paginacao);
}