package org.acme.ws;

import java.util.List;

import org.acme.dto.CurrentUser;
import org.acme.dto.NeedUser;
import org.acme.rcd.RcdChats;
import org.acme.rcd.RcdMember;
import org.acme.srv.SrvChats;
import org.acme.srv.SrvMember;

import io.quarkus.logging.Log;
import jakarta.enterprise.context.control.ActivateRequestContext;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("msg")
@ActivateRequestContext
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class WsMessage {

	@Inject
	WsChats wsChats;
	@Inject
	CurrentUser currentUser;
	@Inject
	SrvChats srvChats;
	@Inject
	SrvMember srvMember;

	@Path("private/{to}/{message}")
	@GET
	@NeedUser
	public void sendPrivate(@PathParam("to") String to,
			@PathParam("message") String message) {
		Log.info("WsMsg " + to + " " + message);
		wsChats.send(currentUser.getMemId(), to, message);
	}

	@GET
	public void sendPrivate2() {
		Log.info("WsMsg");
	}

	@Path("private/myMessage/{getter}")
	@GET
	@NeedUser
	public List<RcdChats> getMyMessage(@PathParam("getter") String getter) {
		RcdMember mem = srvMember.getDataByUsername(getter);
		List<RcdChats> lst = srvChats.getChats(currentUser.getMemId(), mem.getId());
		for (RcdChats rcdChats : lst) {
			rcdChats.getGetterId().setPostCollection(null);
			rcdChats.getGetterId().setPassword(null);
			rcdChats.getGetterId().setToken(null);
			rcdChats.getSenderId().setPostCollection(null);
			rcdChats.getSenderId().setPostCollection(null);
			rcdChats.getSenderId().setPassword(null);
			rcdChats.getSenderId().setToken(null);
		}
		return lst;
	}

	// @Path("private/{message}")
	// @GET
	// @NeedUser
	// public void notif(@PathParam("message") String message) {
	// Log.info(message);
	// // wsChats.send(currentUser.getMemId(), message);
	// }

}
