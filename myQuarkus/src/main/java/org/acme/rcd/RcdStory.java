package org.acme.rcd;

import java.io.Serializable;
import java.sql.Timestamp;
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
@Table(name = "story")
public class RcdStory implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id")
	public Integer id;
	@Column(name = "imgUrl")
	private String imgUrl;
	@Column(name = "start_show")
	private Timestamp startShow;
	@Column(name = "end_show")
	private Timestamp endShow;
	@ManyToOne
	@JoinColumn(name = "mem_id", referencedColumnName = "id")
	private RcdMember memId;

	public RcdStory() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public Timestamp getStartShow() {
		return startShow;
	}

	public void setStartShow(Timestamp startShow) {
		this.startShow = startShow;
	}

	public Timestamp getEndShow() {
		return endShow;
	}

	public void setEndShow(Timestamp endShow) {
		this.endShow = endShow;
	}

	public RcdMember getMemId() {
		return memId;
	}

	public void setMemId(RcdMember memId) {
		this.memId = memId;
	}

}