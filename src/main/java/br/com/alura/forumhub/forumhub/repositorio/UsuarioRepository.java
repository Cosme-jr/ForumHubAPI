package br.com.alura.forumhub.forumhub.repositorio; // Certifique-se que o nome do pacote está correto

import br.com.alura.forumhub.forumhub.usuario.Usuario; // Importa a entidade Usuario
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails; // Importa UserDetails

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Método para o Spring Security buscar o usuário pelo login
    UserDetails findByLogin(String login);
}