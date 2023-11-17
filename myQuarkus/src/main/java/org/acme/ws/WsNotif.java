package org.acme.ws;

import java.util.List;

import org.acme.dto.CurrentUser;
import org.acme.dto.NeedUser;
import org.acme.rcd.RcdNotif;
import org.acme.srv.SrvNotif;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

/**
 *
 * @author
 */
@Path("notif")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class WsNotif {

	@Inject
	private SrvNotif srvNotif;

	@Inject
	private CurrentUser currentUser;

	@Path("list")
	@GET
	@NeedUser
	public List<RcdNotif> notif() {
		int memId = currentUser.getMemId();
		List<RcdNotif> lst = srvNotif.findByMem(memId);
		System.out.println("dapat data member");
		for (RcdNotif rcdNotif : lst) {
			rcdNotif.getMemId().setPostCollection(null);
			rcdNotif.getMemId().setPassword(null);
			rcdNotif.getMemId().setToken(null);
		}
		return lst;
	}
}
