package org.acme.ws;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;



import org.acme.rcd.RcdChats;
import org.acme.rcd.RcdMember;
import org.acme.rcd.RcdNotif;
import org.acme.srv.SrvChats;
import org.acme.srv.SrvMember;
import org.acme.srv.SrvNotif;

import io.quarkus.logging.Log;
import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.control.ActivateRequestContext;
import jakarta.inject.Inject;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;

@ServerEndpoint("/chat/{username}")
@ApplicationScoped
@ActivateRequestContext
public class WsChats {

	@Inject
	private SrvMember srvMember;

	@Inject
	private SrvChats srvChats;

	@Inject
	private SrvNotif srvNotif;

	// Map to store active sessions
	private static final Map<String, Session> sessions = new ConcurrentHashMap<>();

	@OnOpen
	public void onOpen(Session session, @PathParam("username") String username) {
		// broadcast("User " + username + " joined");
			sessions.put(username, session);
	}

	@OnClose
	public void onClose(Session session, @PathParam("username") String username) {
		sessions.remove(username);
		// broadcast("User " + username + " left");
	}

	@OnError
	public void onError(Session session, @PathParam("username") String username, Throwable throwable) {
		sessions.remove(username);
		// broadcast("User " + username + " left on error: " + throwable);
	}

	// @OnMessage
	// public void onMessage(String message, Session session) {
	// System.out.println("Received message: " + message + " from session " +
	// session.getId());

	// // Broadcast the received message to all connected sessions
	// try {
	// session.getBasicRemote().sendText("Server: " + message);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }

	public void sendMessageToClient(int senderId, String username, String message) {
		Session s = sessions.get(username);
		if (s != null && s.isOpen()) {
			// try {
			// Create a JSON object with your message
			// JsonObject jsonMessage = new JsonObject();
			// jsonMessage.put("message", message);

			// // Send the JSON as a string
			// s.getBasicRemote().sendText(jsonMessage.toString());

			RcdMember sender = srvMember.getMyProfil(senderId);
			sender.setToken(null);
			sender.setPassword(null);
			sender.setPostCollection(null);
			RcdMember getter = srvMember.getDataByUsername(username);
			getter.setToken(null);
			getter.setPassword(null);
			getter.setPostCollection(null);
			try {
				RcdChats rcdChats = new RcdChats();
				rcdChats.setId(0);
				rcdChats.setSenderId(sender);
				rcdChats.setGetterId(getter);
				rcdChats.setMessages(message);
				JsonObject jsonMessage = new JsonObject();
				jsonMessage.put("message", rcdChats);

				// Send the JSON as a string
				s.getBasicRemote().sendText(jsonMessage.toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// }

	}

	public void send(int myId, String username, String message) {
		Session s = sessions.get(username);
		if (s != null && s.isOpen()) {
			Log.info("dapat sssion");
			HashMap<String, String> result = new HashMap<>();
			result.put("message", message);
			s.getAsyncRemote().sendObject(result);
		} else {
			Log.info("session penerima null");
		}

		sendMessageToClient(myId, username, message);
		saveMessageToDatabase(myId, username, message);
	}

public void saveMessageToDatabase(int senderId, String username, String message) {
		RcdChats rcdChats = new RcdChats();
		RcdMember sender = srvMember.getMyProfil(senderId);
		RcdMember getter = srvMember.getDataByUsername(username);

		rcdChats.setSenderId(sender);
		rcdChats.setGetterId(getter);
		rcdChats.setMessages(message);
		srvChats.insert(rcdChats);

	LocalDateTime now = LocalDateTime.now();
	Timestamp time = Timestamp.valueOf(now);

		RcdNotif notif = new RcdNotif();
		notif.setNotif("You Have Message From " + sender.getUsername());
		notif.setMemId(getter);
		notif.setTime(time);
		srvNotif.insert(notif);
	}

}
