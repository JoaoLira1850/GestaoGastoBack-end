package controleGastos.Gestao.infra.security;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import controleGastos.Gestao.user.Usuario;
import controleGastos.Gestao.user.UsuarioDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {


    @Value("${gestao.security.token}")
    String secret;


    public String createToken(Usuario usuario){

        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);

             String tokenJWT = JWT.create()
                     .withIssuer("Joao Vitor")
                     .withSubject(usuario.getLogin())
                     .withExpiresAt(dateExpiret())
                     .sign(algorithm);
             return tokenJWT;
        } catch (JWTCreationException e) {
            throw new RuntimeException("Falha em criar token !!");
        }


    }



    public Instant dateExpiret(){


        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));

    }


    public String verifyTokenJWT(String tokenJWT){

        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            String tokenVeriry = JWT.require(algorithm)
                    .withIssuer("Joao Vitor")
                    .build()
                    .verify(tokenJWT)
                    .getSubject();

            return tokenVeriry;
        }catch (JWTVerificationException e){

            throw new JWTVerificationException("Token Invalido");

        }

    }


}
