package org.acme.dto;
import java.security.Principal;

import jakarta.ws.rs.core.SecurityContext;

public class MySecurityContext implements SecurityContext {

	private String username;
	private String role;

	// private class MyPrincipal implements Principal {
	// 	@Override
	// 	public String getName() {
	// 		return username;
	// 	}
	// }

	

	@Override
	public Principal getUserPrincipal() {
		return new Principal() {
			@Override
			public String getName() {
				return username;
			}
		};
	}

	public MySecurityContext(String username, String role) {
		this.username = username;
		this.role = role;
	}

	@Override
	public boolean isUserInRole(String role) {
		return role.contains(this.role);
	}

	@Override
	public boolean isSecure() {
		return true;
	}

	@Override
	public String getAuthenticationScheme() {
		return "";
	}
	
}
