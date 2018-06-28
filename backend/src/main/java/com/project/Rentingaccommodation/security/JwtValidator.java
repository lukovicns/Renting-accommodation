package com.project.Rentingaccommodation.security;



import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Component
public class JwtValidator {

    private String secret = "SECRETKEY";

    public JwtUser validate(String token) {
    	JwtUser jwtUser = null;
        try {
            Claims body = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
            jwtUser = new JwtUser(
        		Long.parseLong(body.get("id").toString()),
        		(String) body.get("email"),
        		(String) body.get("role"),
        		(String) body.get("status")
            );
        }
        catch (Exception e) {
            System.out.println(e);
        }
        
        return jwtUser;
    }
    
    public JwtAgent validateAgent(String token) {
    	JwtAgent jwtAgent = null;
        try {
            Claims body = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
            jwtAgent = new JwtAgent(
        		Long.parseLong(body.get("id").toString()),
        		(String) body.get("email"),
        		(String) body.get("role")
            );
        }
        catch (Exception e) {
            System.out.println(e);
        }
        
        return jwtAgent;
    }
    
    public JwtAdmin validateAdmin(String token) {
    	JwtAdmin jwtAdmin = null;
        try {
            Claims body = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
            jwtAdmin = new JwtAdmin(
        		Long.parseLong(body.get("id").toString()),
        		(String) body.get("email"),
        		(String) body.get("role")
            );
        }
        catch (Exception e) {
            System.out.println(e);
        }
        
        return jwtAdmin;
    }
}
