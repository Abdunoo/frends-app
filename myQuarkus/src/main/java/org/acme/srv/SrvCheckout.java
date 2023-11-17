package org.acme.srv;

import org.acme.rcd.RcdCheckout;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

/**
 *
 * @author trainee
 */
@ApplicationScoped
@Transactional
public class SrvCheckout {

	@Inject
	private EntityManager em;

	public void insert(RcdCheckout rcdCheckout) {
		em.merge(rcdCheckout);
	}
}
