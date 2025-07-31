package br.com.alura.forumhub.forumhub.controller; // Ajuste o pacote conforme a sua estrutura

import br.com.alura.forumhub.forumhub.usuario.DadosAutenticacao; // Importa o DTO de entrada de login
import br.com.alura.forumhub.forumhub.usuario.DadosTokenJWT;    // Importa o DTO de saída do token
import br.com.alura.forumhub.forumhub.usuario.Usuario;           // Importa a entidade Usuario
import br.com.alura.forumhub.forumhub.servico.TokenService;       // Importa o TokenService
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // Marca a classe como um controlador REST
@RequestMapping("/login") // Define o endpoint base para este controlador
public class AuthController {

    @Autowired
    private AuthenticationManager manager; // Injeta o AuthenticationManager do Spring Security

    @Autowired
    private TokenService tokenService; // Injeta o TokenService para gerar JWTs

    @PostMapping // Mapeia requisições POST para /login
    public ResponseEntity<DadosTokenJWT> efetuarLogin(@RequestBody @Valid DadosAutenticacao dados) {
        // Cria um objeto de token de autenticação com as credenciais fornecidas
        // Este objeto será passado para o AuthenticationManager
        var authenticationToken = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());

        // Tenta autenticar o usuário usando o AuthenticationManager
        // Se as credenciais estiverem corretas, retorna um objeto Authentication
        // que contém os detalhes do usuário autenticado (sua entidade Usuario, pois ela implementa UserDetails)
        var authentication = manager.authenticate(authenticationToken);

        // Se a autenticação foi bem-sucedida, gera o token JWT
        // O authentication.getPrincipal() retorna o objeto UserDetails, que é a sua entidade Usuario
        var tokenJWT = tokenService.gerarToken((Usuario) authentication.getPrincipal());

        // Retorna o token JWT encapsulado no DTO DadosTokenJWT com status HTTP 200 OK
        return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));
    }
}