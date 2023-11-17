package org.acme.srv;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;


import org.acme.rcd.RcdStory;

import io.quarkus.logging.Log;

/**
 *
 * @author trainee
 */
@ApplicationScoped
@Transactional
public class SrvStory {

	@Inject
	private EntityManager em;

	public void insert(RcdStory rcdStory) {
		em.merge(rcdStory);
	}

	public void detach(Object record) {
		em.detach(record);
	}

	public void delete(int id) {
		em.remove(findStoryById(id));
	}

	public List<RcdStory> findAll() {
		TypedQuery<RcdStory> tq = em.createQuery("SELECT b FROM RcdStory b LEFT JOIN FETCH b.memId",
				RcdStory.class);
		return tq.getResultList();
	}

	public RcdStory findById(int memId) {
		TypedQuery<RcdStory> tq = em.createQuery(
				"SELECT b FROM RcdStory b LEFT JOIN FETCH b.memId WHERE b.memId.id = :id",
				RcdStory.class);
		tq.setParameter("id", memId);
		try {
			return tq.getSingleResult();
		} catch (NoResultException e) {
			 Log.debug(e, e);
			 return null;
		}
	}

	public List<RcdStory> findExceptMe(int memId) {
		TypedQuery<RcdStory> tq = em.createQuery(
				"SELECT b FROM RcdStory b LEFT JOIN FETCH b.memId WHERE b.memId.id <> :id",
				RcdStory.class);
		tq.setParameter("id", memId);
		return tq.getResultList();
	}

	public RcdStory findStoryById(int id) {
		TypedQuery<RcdStory> tq = em.createQuery(
				"SELECT b FROM RcdStory b LEFT JOIN FETCH b.memId WHERE b.id = :id",
				RcdStory.class);
		tq.setParameter("id", id);
		return tq.getSingleResult();
	}
}
