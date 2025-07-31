package br.com.alura.forumhub.forumhub.seguranca;// Ou qualquer pacote temporário

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class GeradorDeSenha {

    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String senhaPura = "123456"; // Sua senha de teste
        String senhaCriptografada = encoder.encode(senhaPura);
        System.out.println("Senha pura: " + senhaPura);
        System.out.println("Senha criptografada (BCrypt): " + senhaCriptografada);
    }
}