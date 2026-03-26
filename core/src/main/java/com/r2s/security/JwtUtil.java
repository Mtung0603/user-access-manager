package com.r2s.security;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.Date;

@Component
public class JwtUtil {
    @Value("${jwt.secret}")
    private  String  SECRET_KEY   ;

    public String generateToken(String username ){
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration((new Date(System.currentTimeMillis()+1000*60*60*10)))
                .signWith(SignatureAlgorithm.HS256 , SECRET_KEY)
                .compact() ;
    }
    public String extractUsername(String token){
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getSubject() ;
    }

    public boolean validateToken(String token , UserDetails userDetails){
        final String username = extractUsername(token);
        System.out.println("Token username: " + username);
        System.out.println("UserDetails username: " + userDetails.getUsername());
        System.out.println("Is expired: " + isTokenExpired(token));
        System.out.println("Authorities: " + userDetails.getAuthorities());
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token)) ;

    }
    private boolean isTokenExpired(String token ){
        return Jwts.parser()
                . setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getExpiration()
                .before(new Date()) ;
    }
}
