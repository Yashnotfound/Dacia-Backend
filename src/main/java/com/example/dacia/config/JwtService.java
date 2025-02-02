package com.example.dacia.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class JwtService {
    private static final String SECRET = "1993ae5f0b8941fa634b379706a3e7b7e05554a2c26bb856883577a2d408310073f9291d36e94dfd800a7b7f83eb588dff431bc234a16691d8a4949e9bf494f3" +
            "7dcb372dedecb22487cd75188ae823a9e5e79cf52438e94f4f39733a186dda1ae6641605b33020bf7e85cd59621ff171e062b065f4bef7a208ecfd4e15b19b3c" +
            "d4c885796363e741d24377c32c2e3b37645d56656aa0492f9be8fcc120f67950c6b7c2becdd61cb55a866744df8c8e4f5499d052f7472aa8c2e54e36e9002834" +
            "8ad3e1c9b1b11b3c66450c8086ca07bb25f03ac38e9ab96e140657d8b18967eda03e4456659e13a4b7450b36d197ca8353701cbda17e759f7c3e87de8185bfa5" +
            "bbdbb1f7090e26f6b2781d1d4797480ba043fe5f2ff50848feebf6d0085e4247074631aa5b7cb2a7dc2eecfa3ce8986b454ab412211226c1358a7206b46f8961";
    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public List<String> extractRoles(String token) {
        Claims claims = extractAllClaims(token);

        // Extract "role" array from claims
        List<Map<String, String>> roleObjects = claims.get("role", List.class);

        if (roleObjects == null) {
            return List.of(); // No roles found
        }

        // Convert each map object to role strings
        return roleObjects.stream()
                .map(role -> role.get("authority")) // Extract "authority" value
                .collect(Collectors.toList());
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", userDetails.getAuthorities());
        return generateToken(claims, userDetails);
    }
    public boolean isTokenValid(String token,UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date(System.currentTimeMillis()));
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000000*60*24))
                .signWith(getSignInKey(),SignatureAlgorithm.HS512)
                .compact();
    }
    public Claims extractAllClaims(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
