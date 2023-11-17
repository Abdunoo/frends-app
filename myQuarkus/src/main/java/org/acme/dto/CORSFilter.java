package org.acme.dto;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.Provider;

/**
 *
 * @author amrullah
 */
@Provider
public class CORSFilter implements ContainerResponseFilter {

	private static Logger log = LoggerFactory.getLogger(CORSFilter.class);

	@Override
	public void filter(ContainerRequestContext requestContext, ContainerResponseContext cres) throws IOException {
		// cres.getHeaders().add("Access-Control-Allow-Origin", requestContext.getHeaderString("Origin"));
		cres.getHeaders().add("Access-Control-Allow-Headers", "origin, content-type, accept, authorization, token");
		// cres.getHeaders().add("Access-Control-Allow-Credentials", "true");
		cres.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
		cres.getHeaders().add("Access-Control-Max-Age", "1209600");
	}

}
