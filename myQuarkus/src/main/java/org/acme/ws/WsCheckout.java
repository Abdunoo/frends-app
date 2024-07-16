package org.acme.ws;

import org.acme.dto.CurrentUser;
import org.acme.dto.NeedUser;
import org.acme.rcd.RcdCheckout;
import org.acme.rcd.RcdMarket;
import org.acme.rcd.RcdMember;
import org.acme.srv.SrvCheckout;
import org.acme.srv.SrvMarket;
import org.acme.srv.SrvMember;

import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.context.control.ActivateRequestContext;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

/**
 *
 * @author
 */
@Path("checkout")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class WsCheckout {

	@Inject
	SrvCheckout srvCheckout;
	@Inject
	CurrentUser currentUser;
	@Inject
	SrvMember srvMember;
	@Inject
	SrvMarket srvMarket;

	@Path("insert")
	@POST
	@NeedUser
	@RequestScoped
	@ActivateRequestContext
	public void insert(RcdCheckout rcdCheckout) {
		RcdMember buyer = srvMember.getMyProfil(currentUser.getMemId());
		rcdCheckout.setBuyerId(buyer);
		srvMarket.updateNumberPrd(rcdCheckout.getProductId().id);
		// rcdCheckout.getProductId().setStock(-1);
		// rcdCheckout.getProductId().setSold(+1);;
		srvCheckout.insert(rcdCheckout);
		System.out.println("add checkout_his");
	}
}
