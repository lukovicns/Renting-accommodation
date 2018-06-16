package com.project.Rentingaccommodation.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class JwtUserDetails implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String email;
	private String role;
	private String token;
	private Collection<? extends GrantedAuthority> authorities;

	public JwtUserDetails(String email, String role, String token, List<GrantedAuthority> grantedAuthorities) {
	    this.email = email;
	    this.role = role;
	    this.token = token;
	    this.authorities = grantedAuthorities;
	}
	
	@Override
	public boolean isAccountNonExpired() {
	    return true;
	}
	
	@Override
	public boolean isAccountNonLocked() {
	    return true;
	}
	
	@Override
	public boolean isCredentialsNonExpired() {
	    return true;
	}

	@Override
	public boolean isEnabled() {
	    return true;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
	    return authorities;
	}

	public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String getUsername() {
		return null;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return null;
	}
}
