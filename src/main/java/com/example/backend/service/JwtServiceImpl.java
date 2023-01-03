package com.example.backend.service;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CookieValue;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtServiceImpl implements JwtService {

    private String secretKey = "sjkdfjskdhfbnlkjskdfjilu`21kl3j1234kjsldknflk123j453jksdkfksjkln125431";

    @Override
    public String getToken(String key, Object value) {

        Date expTime = new Date();
        expTime.setTime(expTime.getTime() + 1000 * 60 * 15);
        byte[] byeSecretKey = DatatypeConverter.parseBase64Binary(secretKey);
        Key sineKey = new SecretKeySpec(byeSecretKey, SignatureAlgorithm.HS256.getJcaName());

        Map<String, Object> headerMap = new HashMap<>();
        headerMap.put("typ", "JWT");
        headerMap.put("alg", "HS256");

        Map<String, Object> map = new HashMap<>();
        map.put(key, value);

        JwtBuilder jwtBuilder = Jwts.builder()
                .setHeader(headerMap)
                .setClaims(map)
                .setExpiration(expTime)
                .signWith(sineKey, SignatureAlgorithm.HS256);

        return jwtBuilder.compact();

    }

    @Override
    public Claims getClaims(@CookieValue String token) {

        if (token != null && !"".equals(token)) {
            try {
                byte[] byeSecretKey = DatatypeConverter.parseBase64Binary(secretKey);
                Key sineKey = new SecretKeySpec(byeSecretKey, SignatureAlgorithm.HS256.getJcaName());

                return Jwts.parserBuilder().setSigningKey(sineKey).build().parseClaimsJws(token).getBody();
            } catch (ExpiredJwtException e) {

            } catch (JwtException e) {

            }
        }
        return null;
    }

    @Override
    public boolean isValid(String token) {
        return this.getClaims(token) != null;
    }

    @Override
    public int getId(String token) {

        Claims claims = this.getClaims(token);
        if (claims != null) {
            return (int) claims.get("id");
        }
        return 0;
    }
}
