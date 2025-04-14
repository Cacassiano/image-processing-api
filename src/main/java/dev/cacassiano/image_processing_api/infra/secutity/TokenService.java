package dev.cacassiano.image_processing_api.infra.secutity;

import java.time.OffsetDateTime;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;

@Service
public class TokenService {

    @Value("${my_sec_key}")
    private String secretKey;

    @Value("${spring.application.name}")
    private String issuer;
    public String createToken(String email) {
        try {
            Algorithm cripto = Algorithm.HMAC512(secretKey); 
            String token = JWT.create()
                            .withIssuer(issuer)
                            .withSubject(email)
                            .withExpiresAt(OffsetDateTime.now().plusMinutes(15).toInstant())
                            .sign(cripto);
            return token;
        } catch (Exception e) {
            throw new RuntimeException("Failure when create the token");
        }
    }

    public String validateToken(String token){
        try {
            Algorithm cripto = Algorithm.HMAC512(secretKey); 
            return JWT.require(cripto)
                    .withIssuer(issuer)
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
