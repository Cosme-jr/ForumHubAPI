package br.com.alura.forumhub.forumhub.servico; // Ajuste o pacote conforme a sua estrutura

import br.com.alura.forumhub.forumhub.usuario.Usuario; // Importa a entidade Usuario
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${jwt.secret}") // Injeta o valor da propriedade jwt.secret do application.properties
    private String secret;

    @Value("${jwt.expiration.minutes}") // Injeta o valor da propriedade jwt.expiration.minutes
    private Long expirationMinutes;

    public String gerarToken(Usuario usuario) {
        try {
            // Define o algoritmo de assinatura com base na sua chave secreta
            Algorithm algoritmo = Algorithm.HMAC256(secret);
            // Constrói o token JWT
            return JWT.create()
                    .withIssuer("API ForumHub") // Emissor do token
                    .withSubject(usuario.getLogin()) // Assunto do token (geralmente o login do usuário)
                    .withClaim("id", usuario.getId()) // Adiciona um claim personalizado (opcional, mas útil)
                    .withExpiresAt(dataExpiracao()) // Define a data de expiração do token
                    .sign(algoritmo); // Assina o token com o algoritmo e segredo
        } catch (JWTCreationException exception){
            throw new RuntimeException("Erro ao gerar token JWT", exception);
        }
    }

    public String getSubject(String tokenJWT) {
        try {
            // Define o algoritmo para verificar a assinatura
            Algorithm algoritmo = Algorithm.HMAC256(secret);
            // Tenta verificar e decodificar o token
            return JWT.require(algoritmo)
                    .withIssuer("API ForumHub") // Verifica se o emissor é o correto
                    .build()
                    .verify(tokenJWT) // Verifica a assinatura e a validade
                    .getSubject(); // Retorna o assunto (login do usuário)
        } catch (JWTVerificationException exception){
            // Se o token for inválido, expirado ou tiver a assinatura errada
            return null; // Retorna null ou lança uma exceção customizada
        }
    }

    private Instant dataExpiracao() {
        // Define a data de expiração (agora + X minutos, no fuso horário de São Paulo)
        return LocalDateTime.now().plusMinutes(expirationMinutes).toInstant(ZoneOffset.of("-03:00"));
    }
}
