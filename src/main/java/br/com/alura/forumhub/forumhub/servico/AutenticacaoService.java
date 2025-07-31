package br.com.alura.forumhub.forumhub.servico; // Ajuste o pacote conforme a sua estrutura

import br.com.alura.forumhub.forumhub.repositorio.UsuarioRepository; // Importa o repositório de usuários
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service // Marca a classe como um serviço do Spring
public class AutenticacaoService implements UserDetailsService {

    @Autowired // Injeta o repositório de usuários
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        // Este método será chamado pelo Spring Security para carregar os detalhes do usuário
        // Usamos o método findByLogin que criamos no UsuarioRepository
        UserDetails usuario = usuarioRepository.findByLogin(login);
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuário não encontrado com o login: " + login);
        }
        return usuario;
    }
}