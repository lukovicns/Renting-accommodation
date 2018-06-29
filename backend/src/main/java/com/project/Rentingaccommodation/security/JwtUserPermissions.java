package com.project.Rentingaccommodation.security;

import javax.xml.bind.DatatypeConverter;

import org.springframework.stereotype.Component;

import com.project.Rentingaccommodation.model.UserPrivileges;
import com.project.Rentingaccommodation.model.UserRoles;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Component
public class JwtUserPermissions {
	
	public boolean hasRoleAndPrivilege(String token, UserRoles checkRole, UserPrivileges checkPrivilege) {
		String role = getRoleFromToken(token);
		String privilege = getPrivilegeFromToken(token);
		
		if(!role.equals(checkRole.toString()))
			return false;
		if(privilege.equals(checkPrivilege.toString()) || privilege.equals(UserPrivileges.READ_WRITE_PRIVILEGE.toString()))
			return true;
		return false;
	}
	
	public String getRoleFromToken(String token) {
		Claims claims = Jwts.parser()         
			       .setSigningKey(DatatypeConverter.parseBase64Binary("SECRETKEY"))
			       .parseClaimsJws(token.split(" ")[1]).getBody();
		return (String) claims.get("role");
	}
	
	public String getPrivilegeFromToken(String token) {
		Claims claims = Jwts.parser()         
			       .setSigningKey(DatatypeConverter.parseBase64Binary("SECRETKEY"))
			       .parseClaimsJws(token.split(" ")[1]).getBody();
		return (String) claims.get("privilege");
	}
}
