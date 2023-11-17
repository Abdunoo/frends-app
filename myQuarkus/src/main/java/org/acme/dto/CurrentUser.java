package org.acme.dto;

import jakarta.enterprise.context.RequestScoped;

/**
 *
 * @author abdun
 */
@RequestScoped
public class CurrentUser {
	private int memId;

	public int getMemId() {
		return memId;
	}

	public void setMemId(int memId) {
		this.memId = memId;
	}

}
