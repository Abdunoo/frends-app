package org.acme.dto;

import io.quarkus.logging.Log;
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

@NeedUser
@Provider
@Priority(Priorities.AUTHORIZATION)
public class NeedUserFilter implements ContainerRequestFilter {
	private final static Logger log = Logger.getLogger(NeedUserFilter.class);
	@Context
	SecurityContext securityContext;

	@Inject
	private SrvMember srvMember;

	@Inject
	private CurrentUser currentUser;

	@Inject
	SecurityIdentity securityIdentity;

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {

		String token = requestContext.getHeaderString("token");
		RcdMember loginData = srvMember.getMyProfilByToken(token);
		Log.info("filter need user");

		if (loginData == null) {
			log.warn("Forbidden from NeedUserFilter");
			requestContext.abortWith(Response.status(Response.Status.FORBIDDEN).build());
		} else {
			currentUser.setMemId(loginData.getId());
		}
	}

}
