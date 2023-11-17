package org.acme.srv;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;


import org.acme.rcd.RcdNotif;

/**
 *
 * @author trainee
 */
@ApplicationScoped
@Transactional
public class SrvNotif {
	@Inject
	private EntityManager em;

	public List<RcdNotif> findAll() {
		TypedQuery<RcdNotif> tq = em.createQuery("SELECT b FROM RcdNotif b LEFT JOIN FETCH b.memId",
				RcdNotif.class);
		return tq.getResultList();
	}

		public List<RcdNotif> findByMem(int id) {
		TypedQuery<RcdNotif> tq = em.createQuery("SELECT b FROM RcdNotif b LEFT JOIN FETCH b.memId WHERE b.memId.id = :id",
				RcdNotif.class);
				tq.setParameter("id", id);

		return tq.getResultList();
	}

	public void detach(Object record) {
		em.detach(record);
	}

	public void insert(RcdNotif rcdnNotif) {
		em.merge(rcdnNotif);
	}
}
