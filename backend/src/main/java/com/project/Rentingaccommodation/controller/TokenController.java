package com.project.Rentingaccommodation.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.project.Rentingaccommodation.security.JWTGenerator;

@RestController
@RequestMapping(value="/token")
public class TokenController {

    private JWTGenerator jwtGenerator;

    public TokenController(JWTGenerator jwtGenerator) {
        this.jwtGenerator = jwtGenerator;
    }

   /* @PostMapping
    public ResponseEntity<Token> generate(@RequestBody JWTUser jwtUser) {
    	if (jwtUser.getEmail() == null || jwtUser.getPassword() == null) {
    		return new ResponseEntity<>(new Token(null, "You must provide valid email and password."), HttpStatus.FORBIDDEN);
    	}
        return new ResponseEntity<>(new Token(jwtGenerator.generate(jwtUser), null), HttpStatus.OK);
    }*/
}
