package org.acme.rcd;

import java.io.Serializable;

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
@Table(name = "checkout_his")
public class RcdCheckout implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id")
	public Integer id;
	@Column(name = "first_name")
	private String firstName;
	@Column(name = "last_name")
	private String lastName;
	@Column(name = "email")
	private String email;
	@Column(name = "street_address")
	private String streetAddress;
	@Column(name = "province")
	private String province;
	@Column(name = "city")
	private String city;
	@Column(name = "pos_code")
	private Integer posCode;
	@Column(name = "phone")
	private String phone;
	@ManyToOne
	@JoinColumn(name = "productId", referencedColumnName = "id")
	private RcdMarket productId;
	@ManyToOne
	@JoinColumn(name = "buyerId", referencedColumnName = "id")
	private RcdMember buyerId;

	RcdCheckout() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getStreetAddress() {
		return streetAddress;
	}

	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Integer getPosCode() {
		return posCode;
	}

	public void setPosCode(Integer posCode) {
		this.posCode = posCode;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}



	public RcdMarket getProductId() {
		return productId;
	}

	public void setProductId(RcdMarket productId) {
		this.productId = productId;
	}

	public RcdMember getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(RcdMember buyerId) {
		this.buyerId = buyerId;
	}

	

}
