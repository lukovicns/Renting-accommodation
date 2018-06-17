package com.project.Rentingaccommodation.security;

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
        User user = userService.findByEmail(jwtUser.getEmail());
        if(user != null) {
//        	claims.put("id", jwtUser.getId());
        	claims.put("email", jwtUser.getEmail());
    		claims.put("role", UserRoles.USER);
    		return Jwts.builder()
                    .setClaims(claims)
                    .signWith(SignatureAlgorithm.HS512, "secretKey")
                    .compact();
    		
        }
        return "User with email " + jwtUser.getEmail() + " not found.";
    }
}
