package org.acme.srv;

import java.util.List;

import org.acme.rcd.RcdMarket;
import org.acme.rcd.RcdNotif;

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
public class SrvMarket {

	@Inject
	private EntityManager em;

	public void insert(RcdMarket rcdMarket) {
		em.merge(rcdMarket);
	}

	public void updateNumberPrd(int id) {
		RcdMarket prd = findById(id);
		prd.setStock(prd.getStock() - 1);
		prd.setSold(prd.getSold() + 1);
		em.merge(prd);
	}

	public void detach(Object record) {
		em.detach(record);
	}

	public List<RcdMarket> findAll() {
		TypedQuery<RcdMarket> tq = em.createQuery(
				"SELECT b FROM RcdMarket b LEFT JOIN FETCH b.sellerId ORDER BY b.id DESC",
				RcdMarket.class);
		return tq.getResultList();
	}

	public List<RcdMarket> findMyProduct(int id) {
		TypedQuery<RcdMarket> tq = em.createQuery(
				"SELECT b FROM RcdMarket b LEFT JOIN FETCH b.sellerId WHERE b.sellerId.id = :id ORDER BY b.id DESC",
				RcdMarket.class);
		tq.setParameter("id", id);
		return tq.getResultList();
	}

	public RcdMarket findById(int id) {
		// TypedQuery<RcdMarket> tq = em.createQuery("SELECT b FROM RcdMarket b LEFT
		// JOIN FETCH b.sellerId ORDER BY b.id DESC",
		// RcdMarket.class);
		// return tq.getSingleResult()
		RcdMarket rcdMarket = em.find(RcdMarket.class, id);
		return rcdMarket;
	}

	public void delete(int id) {
		em.remove(findById(id));
	}

}
