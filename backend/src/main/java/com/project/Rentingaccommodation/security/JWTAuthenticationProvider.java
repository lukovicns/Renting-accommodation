package com.project.Rentingaccommodation.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JWTAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    @Autowired
    private JWTValidator validator;

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {
    	
    }

    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {

        JWTAuthenticationToken jwtAuthenticationToken = (JWTAuthenticationToken) usernamePasswordAuthenticationToken;
        String token = jwtAuthenticationToken.getToken();

        JWTUser jwtUser = validator.validate(token);

        if (jwtUser == null) {
            throw new RuntimeException("JWT token is incorrect.");
        }

        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList(jwtUser.getRole().toString());
        return new JWTUserDetails(jwtUser.getEmail(), jwtUser.getPassword(), jwtUser.getRole(), token, grantedAuthorities);
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return (JWTAuthenticationToken.class.isAssignableFrom(aClass));
    }
}
