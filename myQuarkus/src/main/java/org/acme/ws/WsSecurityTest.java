package org.acme.ws;

import io.quarkus.logging.Log;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.Path;

@Path("sec")
@ApplicationScoped
public class WsSecurityTest {

	@Path("public")
	public void testNoSecurity() {
		Log.info("should be accessible without login");
	}

	@Path("private1")
	@RolesAllowed("admin")
	public void testPrivate1() {
		Log.info("should be accessible after login");
	}
}
