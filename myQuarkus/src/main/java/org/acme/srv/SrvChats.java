package org.acme.srv;

import java.util.List;



import org.acme.rcd.RcdChats;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

/**
 *
 * @author trainee
 */
@ApplicationScoped
@Transactional
public class SrvChats {
	@Inject
	private EntityManager em;

	public void insert(RcdChats rcdChats) {
		em.merge(rcdChats);
	}

	public void detach(Object record) {
		em.detach(record);
	}

	public List<RcdChats> getChats(int senderId, int getterId) {
		TypedQuery<RcdChats> tq = em.createQuery("SELECT b FROM RcdChats b WHERE (b.senderId.id = :senderId OR b.getterId.id = :senderId) AND (b.senderId.id = :getterId OR b.getterId.id = :getterId)", RcdChats.class);
		tq.setParameter("senderId", senderId);
		tq.setParameter("getterId", getterId);
		return tq.getResultList();
	}
}
