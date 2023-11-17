package org.acme.dto;

import io.quarkus.security.AuthenticationCompletionException;
import io.quarkus.security.UnauthorizedException;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import jakarta.ws.rs.ext.Provider;

import org.acme.rcd.RcdMember;
import org.acme.srv.SrvMember;
import org.jboss.logging.Logger;

import java.io.IOException;
import java.security.Principal;

@NeedPermission
@Provider
@Priority(Priorities.AUTHORIZATION)
public class NeedPermissionFilter implements ContainerRequestFilter {
	private final static Logger log = Logger.getLogger(NeedPermissionFilter.class);
	@Context
	SecurityContext securityContext;

	@Inject
	private SrvMember srvMember;

	@Inject
	SecurityIdentity securityIdentity;

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {

		String token = requestContext.getHeaderString("token");
		RcdMember loginData = srvMember.getMyProfilByToken(token);

		if (loginData.getRole() == null || loginData.getRole() == "user") {
			log.warn("Forbidden from Filter");
			requestContext.abortWith(Response.status(Response.Status.FORBIDDEN).build());
		}
	}

}
