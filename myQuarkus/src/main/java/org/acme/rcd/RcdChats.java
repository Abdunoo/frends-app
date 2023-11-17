package org.acme.rcd;

import java.io.Serializable;
import java.util.Collection;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;



/**
 *
 * @author trainee
 */
@Entity
@Table(name = "chats")
public class RcdChats implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id")
	public Integer id;
	@ManyToOne
	@JoinColumn(name = "senderId", referencedColumnName = "id")
	private RcdMember senderId;
	@ManyToOne
	@JoinColumn(name = "getterId", referencedColumnName = "id")
	private RcdMember getterId;
	@Column(name = "messages")
	private String messages;

	public RcdChats() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public RcdMember getSenderId() {
		return senderId;
	}

	public void setSenderId(RcdMember senderId) {
		this.senderId = senderId;
	}

	public RcdMember getGetterId() {
		return getterId;
	}

	public void setGetterId(RcdMember getterId) {
		this.getterId = getterId;
	}

	public String getMessages() {
		return messages;
	}

	public void setMessages(String messages) {
		this.messages = messages;
	}

}
