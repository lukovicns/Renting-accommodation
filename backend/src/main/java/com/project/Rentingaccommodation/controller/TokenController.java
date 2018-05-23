package com.project.Rentingaccommodation.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.project.Rentingaccommodation.security.JWTGenerator;
import com.project.Rentingaccommodation.security.JWTUser;

@RestController
@RequestMapping(value="/token")
public class TokenController {

    private JWTGenerator jwtGenerator;

    public TokenController(JWTGenerator jwtGenerator) {
        this.jwtGenerator = jwtGenerator;
    }

    @PostMapping
    public String generate(@RequestBody JWTUser jwtUser) {
    	if (jwtUser.getEmail() == null || jwtUser.getPassword() == null) {
    		return "You must provide valid email and password.";
    	}
        return jwtGenerator.generate(jwtUser);
    }
}
