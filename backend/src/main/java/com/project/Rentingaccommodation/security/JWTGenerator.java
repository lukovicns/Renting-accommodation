package com.project.Rentingaccommodation.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.project.Rentingaccommodation.model.User;
import com.project.Rentingaccommodation.model.UserRoles;
import com.project.Rentingaccommodation.service.UserService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import javassist.NotFoundException;

@Component
public class JWTGenerator {
	
	@Autowired
	private UserService userService;
	
	private boolean userExists = false;

    public String generate(JWTUser jwtUser) {

        Claims claims = Jwts.claims();
        
        for (User u : userService.findAll()) {
        	// Poredjenje i dekriptovanih lozinki se mora odraditi.
        	if (u.getEmail().equals(jwtUser.getEmail())) {
                claims.put("email", jwtUser.getEmail());
                claims.put("password", jwtUser.getPassword());
        		claims.put("role", UserRoles.USER);
        		userExists = true;
        		break;
        	}
        }
        
        if (userExists) {
            return Jwts.builder()
                    .setClaims(claims)
                    .signWith(SignatureAlgorithm.HS512, "secretKey")
                    .compact();
        }
        
        return "User with email " + jwtUser.getEmail() + " not found.";
    }
}
