package com.project.Rentingaccommodation.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.project.Rentingaccommodation.model.User;
import com.project.Rentingaccommodation.model.UserRoles;
import com.project.Rentingaccommodation.service.UserService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtGenerator {
	
	@Autowired
	private UserService userService;

    public String generateUser(JwtUser jwtUser) {
    	Claims claims = Jwts.claims();
        User user = userService.findOne(jwtUser.getId());
        if(user != null) {
        	claims.put("id", jwtUser.getId());
        	claims.put("email", jwtUser.getEmail());
    		claims.put("role", UserRoles.USER);
    		claims.put("status", user.getStatus());
    		return Jwts.builder()
                    .setClaims(claims)
                    .setExpiration(new Date(System.currentTimeMillis() + (10 * 60 * 1000)))
                    .signWith(SignatureAlgorithm.HS512, "SECRETKEY")
                    .compact();
    		
        }
        return "User with email " + jwtUser.getEmail() + " not found.";
    }

	public String generateAdmin(JwtAdmin jwtAdmin) {
    	Claims claims = Jwts.claims();
    	claims.put("id", jwtAdmin.getId());
    	claims.put("email", jwtAdmin.getEmail());
		claims.put("role", UserRoles.ADMIN);
		return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, "SECRETKEY")
                .compact();
	}
	
	public String generateAgent(JwtAgent jwtAgent) {
    	Claims claims = Jwts.claims();
    	claims.put("id", jwtAgent.getId());
    	claims.put("email", jwtAgent.getEmail());
		claims.put("role", UserRoles.AGENT);
		return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, "SECRETKEY")
                .compact();
	}
}
