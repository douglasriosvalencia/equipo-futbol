package com.soccertraining.soccer_training_api.service.jwt;

import com.soccertraining.soccer_training_api.entity.User;
import com.soccertraining.soccer_training_api.enums.Role;
import com.soccertraining.soccer_training_api.exception.JwtAuthenticationException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
@Log4j2 //para imprimir los logs
public class JwtService {
    /**
     * extracion de la llave secreta
     */
    @Value("${security.jwt.secret-key}")
    private String secretKey;
    /**
     * extracion de la expiracion
     */

    @Value("${security.jwt.token-expiration}")
    private Long tokenExpiration;

    /**
     * modificacion de la llave secrta a bytes
     * @return
     */
    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Generacion del token
     */

    // generamos el token
    public String generateToken(User user){
        //profesional
        log.info("Generando token para usuario: {}", user.getEmail());

        return Jwts.builder()
                .claims(Map.of("userId",user.getId(),
                        "role",user.getRole().name(),
                        "name", user.getName()+" "+user.getSurName()

                ))//datos del usuario
                .subject(user.getEmail())//parametro unico
                .issuedAt(new Date())//fecha de creacion del token
                .expiration(new Date(System.currentTimeMillis()+tokenExpiration))//expiaracion del token
                .signWith(getSigningKey())//frima del token con la llave secreta
                .compact();
    }

    //validacion del token si ya expiro o no
    public boolean isTokenValid(String token){
        try {
            // verifica si no ha expirado
            Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token);
            return true;
        }catch (JwtException e){//expecion por si el token ya expiro
            log.error("Token is invalid: "+ e.getMessage());
            return false;
        }catch (Exception e){//expecion mas global
            log.error("Ocurrio un error inesperado: "+e.getMessage());
            return false;
        }
    }


    //extraemos todo los cleim (payload) del token
    //nota: este metodo se crea para que sea mas facil la extarcion de los claims
    public <T> T extractClaims(String token, Function<Claims,T> resolver){
        final Claims claims= Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return resolver.apply(claims);
    }

    //Extrameos el claim de email
    public String extractEmail(String token){
        return extractClaims(token, Claims::getSubject);
    }

    //Extraemos el id del usuario
    public Long extractUserId(String token){
        return extractClaims(token, claims -> claims.get("userId",Long.class));
    }

    //Extraemos el rol del usuario
    public String extractRole(String token){
        return extractClaims(token,claims -> claims.get("role",String.class));
    }

    //Extramemos el nombre de unsario
    public String extractName(String token){
        return extractClaims(token,claims -> claims.get("name",String.class));
    }

    public String refreshToken(String token){
        Claims claims;

        try {
            claims = Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        }catch (ExpiredJwtException e){
            throw new JwtAuthenticationException("Token a expirado ");
        }catch (JwtException e){
            throw new JwtAuthenticationException("Token es invalido ");
        }catch (Exception e){
            throw new JwtAuthenticationException("error procesando token ");
        }
        User user= new User();
        user.setId(claims.get("userId",Long.class));
        user.setEmail(claims.getSubject());
        user.setRole(Role.valueOf(claims.get("role",String.class)));
        return generateToken(user);
    }






}
