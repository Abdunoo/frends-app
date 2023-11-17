package org.acme.srv;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

import org.acme.rcd.RcdPost;

/**
 *
 * @author trainee
 */
@ApplicationScoped
@Transactional
public class SrvPost {
	// @PersistenceContext
	@Inject
	private EntityManager em;

	public List<RcdPost> search(int start, int max) {
		TypedQuery<RcdPost> tq = em.createQuery("SELECT b FROM RcdPost b LEFT JOIN FETCH b.memId ORDER BY b.id DESC",
				RcdPost.class);
		tq.setFirstResult(start);
		tq.setMaxResults(max);
		return tq.getResultList();
	}

	public List<RcdPost> allData() {
		TypedQuery<RcdPost> tq = em.createQuery("SELECT b FROM RcdPost b LEFT JOIN FETCH b.memId WHERE b.imgUrl <> NULL ORDER BY b.id DESC",
				RcdPost.class);
		return tq.getResultList();
	}

	public List<RcdPost> getPosts(int id) {
		TypedQuery<RcdPost> tq = em.createQuery("SELECT b FROM RcdPost b WHERE b.memId.id = :id ORDER BY b.id DESC",
				RcdPost.class);
		tq.setParameter("id", id);
		return tq.getResultList();
	}

	public RcdPost findById(int id) {
		TypedQuery<RcdPost> tq = em.createQuery("SELECT b FROM RcdPost b LEFT JOIN FETCH b.memId WHERE b.id = :id",
				RcdPost.class);
		tq.setParameter("id", id);
		return tq.getSingleResult();
	}

	public void detach(Object record) {
		em.detach(record);
	}

	public void insert(RcdPost rcdPost) {
		em.merge(rcdPost);
	}

	public void update(RcdPost rcdPost) {
		RcdPost newPost = em.find(RcdPost.class, rcdPost.getId());
		newPost.setCaption(rcdPost.getCaption());
		newPost.setImgUrl(rcdPost.getImgUrl());
		em.merge(newPost);
	}

	public void delete(int id) {
		em.remove(findById(id));
	}
	// public void delete(int id) {
	// TypedQuery<RcdPost> tq = em.createQuery("DELETE b FROM RcdPost b WHERE b.id =
	// :id", RcdPost.class);
	// tq.setParameter("id", id);
	// }

	// public Buku findById( int id) {
	// TypedQuery<Buku> tq = em.createQuery("SELECT b FROM Buku b where b.id = :id",
	// Buku.class);
	// tq.setParameter("id", id);
	// return tq.getSingleResult();
	// }

	// public List<Buku> findBukuByData(QueryBuku q) {
	// String sql = "SELECT b FROM Buku b";

	// if ((q.judulBuku != null && !q.judulBuku.isEmpty())) {
	// sql = sql + " WHERE b.judul LIKE :judul ";
	// }
	// if ((q.sortingData != null && !q.sortingData.isEmpty())) {
	// sql = sql + " ORDER BY " + q.sortingData;
	// }
	// if ((q.direction != null && !q.direction.isEmpty())) {
	// sql = sql + " " + q.direction;
	// }
	// System.out.println("sql: " + sql);
	// TypedQuery<Buku> tq = em.createQuery(sql, Buku.class);
	// if (sql.contains(":judul")) {
	// tq.setParameter("judul", "%" + q.judulBuku + "%");
	// }
	// System.out.println("sql: " + sql);
	// return tq.getResultList();

	// }

	// public void insert(Buku buku) {
	// em.persist(buku);
	// }
	// public void update(Buku buku) {
	// em.merge(buku);
	// }
	// public void delete(Buku buku) {
	// em.remove(buku);
	// }

	// public void detach(Object obj) {
	// em.detach(obj);
	// }

	// public Buku findById2(int id) {
	// return em.find(Buku.class, id);
	// }

	// public Buku findById2(TypedQuery<Buku> tq, int id) {
	// return em.find(Buku.class, id);
	// }

	// public void findBukuByJudul(List<Buku> judul) {
	// throw new UnsupportedOperationException("Not supported yet."); // Generated
	// from
	// nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
	// }
	//
	// public Buku findBukuByJudul(Buku judul) {
	// throw new UnsupportedOperationException("Not supported yet."); // Generated
	// from
	// nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
	// }

}
