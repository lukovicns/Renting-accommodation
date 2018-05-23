package com.project.Rentingaccommodation.security;


import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Component
public class JWTValidator {

    private String secret = "secretKey";

    public JWTUser validate(String token) {
    	JWTUser jwtUser = null;
        try {
            Claims body = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
            jwtUser = new JWTUser((String) body.get("email"), (String) body.get("password"));

        }
        catch (Exception e) {
            System.out.println(e);
        }
        
        return jwtUser;
    }
}
