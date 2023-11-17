package org.acme.srv;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

import org.acme.rcd.RcdMember;

/**
 *
 * @author trainee
 */
@ApplicationScoped
@Transactional
public class SrvMember {
	// @PersistenceContext
	@Inject
	private EntityManager em;

	public List<RcdMember> getData() {
		TypedQuery<RcdMember> tq = em.createQuery("SELECT b FROM RcdMember b", RcdMember.class);
		return tq.getResultList();
	}

	public List<RcdMember> otherMem(String username) {
		TypedQuery<RcdMember> tq = em.createQuery("SELECT b FROM RcdMember b WHERE b.username <> :username",
				RcdMember.class);
		tq.setParameter("username", username);
		return tq.getResultList();
	}

	public RcdMember getDataByUsername(String username) {
		TypedQuery<RcdMember> tq = em.createQuery("SELECT b FROM RcdMember b WHERE b.username = :username",
				RcdMember.class);
		tq.setParameter("username", username);
		return tq.getSingleResult();
	}

	public void detach(Object record) {
		em.detach(record);
	}

	public RcdMember login(String username, String password) {
		TypedQuery<RcdMember> tq = em.createQuery(
				"SELECT b FROM RcdMember b where b.username = :username AND b.password = :password", RcdMember.class);
		tq.setParameter("username", username);
		tq.setParameter("password", password);
		try {
			return tq.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public RcdMember getMyProfil(int id) {
		TypedQuery<RcdMember> tq = em.createQuery("SELECT b FROM RcdMember b where b.id = :id",
				RcdMember.class);
		tq.setParameter("id", id);
		try {
			return tq.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public RcdMember getMyProfilByToken(String token) {
		TypedQuery<RcdMember> tq = em.createQuery("SELECT b FROM RcdMember b where b.token = :token", RcdMember.class);
		tq.setParameter("token", token);
		try {
			return tq.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public void insert(RcdMember rcdMember) {
		em.merge(rcdMember);
	}

	public List<RcdMember> search(String searchQuery) {
		TypedQuery<RcdMember> tq = em.createQuery("SELECT b FROM RcdMember b WHERE b.username LIKE :searchQuery",
				RcdMember.class);
		tq.setParameter("searchQuery", "%" + searchQuery + "%");
		return tq.getResultList();
	}

	public void update(RcdMember rcdMember) {
		em.merge(rcdMember);
	}

	public void delete(int id) {
		em.remove(getMyProfil(id));
	}

}