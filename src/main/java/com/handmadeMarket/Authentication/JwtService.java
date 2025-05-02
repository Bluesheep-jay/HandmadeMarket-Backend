package com.handmadeMarket.Authentication;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.function.Function;

@Service
public class JwtService {
    private String SECRET_KEY = "e1379b9ca61f8c26a20dca6e84c83ba3b41c008256739069b07e9ec091c8235b43bf88778e7658af767bb0a60961fb1653bfe94ab1c301a761920286c16ec4efff8f41f00f2427dfac" +
            "678eb1d92a0aa0cfc3e5b741d04b96dc2c6c1ffa9fc31c22ae1762a37860a5aee2c133d3883cb1248febdefd33826e33f9038be997d1b1eaebb0b9521398a5c78329de7d8ded90584beeadc113b706e18ac1e4bc09ffd5a964b6aec7b4d13df0d5f19b4f2af3bc613ebefc83cbf38ea3209a773f181ca676be7ad71fb1de3bcd49c94f7a1dbe29a15e25daa53c08ec3cf41c0b68a54f6f98b2c085f20b4cc37153ce791c903b87a6ea7bb04d956c0699f23a9387965333";

    public JwtService() {
        try{
            KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
            SecretKey sk = keyGen.generateKey();
            SECRET_KEY  = Base64.getEncoder().encodeToString(sk.getEncoded());
        }catch (NoSuchAlgorithmException exception){
            throw new RuntimeException("Error while generating secret key",exception);
        }
    }

    public String generateToken(String email) {
        Map<String, Object> claims = new HashMap<>();

        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(email)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 60*60*1000*24) )
                .and()
                .signWith(getKey())
                .compact();

    }

    private SecretKey getKey(){
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractEmail(String jwtToken) {
        return extractClaim(jwtToken, Claims::getSubject);
    }

    private <T> T extractClaim(String jwtToken, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(jwtToken);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String jwtToken) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(jwtToken)
                .getPayload();
    }

    public boolean validateToken(String jwtToken, UserDetails userDetails) {
        final String email = extractEmail(jwtToken);
        return (email.equals(userDetails.getUsername()) && !isTokenExpired(jwtToken));
    }

    private boolean isTokenExpired(String jwtToken) {
        return extractClaim(jwtToken, Claims::getExpiration).before(new Date());
    }
}
