package org.acme.dto;

import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.context.control.ActivateRequestContext;

/**
 *
 * @author abdun
 */
@RequestScoped
@ActivateRequestContext
public class CurrentUser {
	private int memId;

	public int getMemId() {
		return memId;
	}

	public void setMemId(int memId) {
		this.memId = memId;
	}

}
